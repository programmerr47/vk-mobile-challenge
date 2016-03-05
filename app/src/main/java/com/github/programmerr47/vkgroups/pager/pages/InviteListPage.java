package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.InviteAdapter;
import com.github.programmerr47.vkgroups.adapter.item.InviteItem;
import com.github.programmerr47.vkgroups.background.db.GroupDao;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;
import com.github.programmerr47.vkgroups.utils.AndroidUtils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.util.ArrayList;

import static com.github.programmerr47.vkgroups.utils.AndroidUtils.res;

/**
 * @author Mihail Spitsin
 * @since 2016-03-02
 */
public class InviteListPage extends Page implements InviteItem.InviteItemCallbacks {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    private RecyclerView inviteListView;
    private InviteAdapter inviteAdapter;
    private RecyclerItems<InviteItem> inviteItems;

    @Override
    public void onCreate() {
        inviteItems = new RecyclerItems<>(new ArrayList<InviteItem>());
        requestAllRecommendations();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.single_community_list_layout, null, false);
    }

    @Override
    public void onViewCreated(View pageView) {
        appBarLayout = (AppBarLayout) pageView.findViewById(R.id.appbar_layout);
        toolbar = (Toolbar) pageView.findViewById(R.id.toolbar);
        inviteListView = (RecyclerView) pageView.findViewById(R.id.community_list);

        prepareItemsViews(pageView);
    }

    @Override
    public void onInviteItemClick(View view) {
        int position = inviteListView.getChildAdapterPosition(view);

        Page detailPage = new GroupDetailPage(inviteItems.get(position).getCommunity(), null);
        pagerListener.openPage(detailPage);
    }

    @Override
    public void onNotSureButtonClick(View view) {
        int position = inviteListView.getChildAdapterPosition(view);
        int id = inviteItems.get(position).getCommunity().id;

        VKRequest request = VKApi.groups().join(VKParameters.from(VKApiConst.GROUP_ID, id, VKApiConst.NOT_SURE, 1));
        request.executeWithListener(new InvitationRequestListener(position, R.string.not_sure_message));
    }

    @Override
    public void onAcceptButtonClick(View view) {
        int position = inviteListView.getChildAdapterPosition(view);
        int id = inviteItems.get(position).getCommunity().id;

        VKRequest request = VKApi.groups().join(VKParameters.from(VKApiConst.GROUP_ID, id, VKApiConst.NOT_SURE, 0));
        request.executeWithListener(new InvitationRequestListener(position, R.string.accept_message));
    }

    @Override
    public void onDeclineButtonClick(View view) {
        int position = inviteListView.getChildAdapterPosition(view);
        int id = inviteItems.get(position).getCommunity().id;

        VKRequest request = VKApi.groups().leave(id);
        request.executeWithListener(new InvitationRequestListener(position, R.string.decline_message));
    }

    private void prepareItemsViews(View pageView) {
        inviteAdapter = new InviteAdapter(inviteItems, this);

        inviteListView.setLayoutManager(new LinearLayoutManager(pageView.getContext()));
        inviteListView.setAdapter(inviteAdapter);
    }

    private void requestAllRecommendations() {
        //TODO move it
        VKRequest vkRequest = VKApi.groups().getInvites(VKParameters.from(VKApiConst.EXTENDED, 1));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                super.onComplete(response);
                if (isTransitionAnimating) {
                    uiWorks.add(new Runnable() {
                        @Override
                        public void run() {
                            VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                            GroupDao groupDao = new GroupDao();
                            for (final VKApiCommunityFull group : communityArray) {
                                groupDao.saveGroup(group);
                                InviteItem item = new InviteItem(group);
                                inviteItems.add(item);
                                item.setItemCallbacks(InviteListPage.this);
                                inviteAdapter.notifyItemInserted(inviteItems.size() - 1);
                            }
                        }
                    });
                } else {
                    VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                    GroupDao groupDao = new GroupDao();
                    for (final VKApiCommunityFull group : communityArray) {
                        groupDao.saveGroup(group);
                        InviteItem item = new InviteItem(group);
                        inviteItems.add(item);
                        item.setItemCallbacks(InviteListPage.this);
                        inviteAdapter.notifyItemInserted(inviteItems.size() - 1);
                    }
                }
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Snackbar.make(getPageView(), error.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.try_again, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestAllRecommendations();
                            }
                        })
                        .show();
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

    private class InvitationRequestListener extends VKRequest.VKRequestListener {
        private int position;
        private int messageId;

        public InvitationRequestListener(int position, int messageId) {
            this.position = position;
            this.messageId = messageId;
        }

        @Override
        public void onComplete(VKResponse response) {
            inviteItems.remove(position);
            inviteAdapter.notifyItemRemoved(position);
            Snackbar.make(inviteListView, res().string(messageId), Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onError(VKError error) {
            Snackbar.make(inviteListView, error.toString(), Snackbar.LENGTH_LONG).show();
        }
    }
}
