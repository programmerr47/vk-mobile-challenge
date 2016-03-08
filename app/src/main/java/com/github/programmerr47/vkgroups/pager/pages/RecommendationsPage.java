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
import com.github.programmerr47.vkgroups.adapter.GroupAdapter;
import com.github.programmerr47.vkgroups.adapter.item.CommunityItem;
import com.github.programmerr47.vkgroups.adapter.item.MyCommunityItem;
import com.github.programmerr47.vkgroups.background.db.GroupDao;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.util.ArrayList;

/**
 * @author Mihail Spitsin
 * @since 2/26/2016
 */
public class RecommendationsPage extends Page {

    private AppBarLayout appBarLayout;

    private RecyclerView recommendListView;
    private GroupAdapter recommendAdapter;
    private RecyclerItems<CommunityItem> recommendItems;

    @Override
    public void onCreate() {
        recommendItems = new RecyclerItems<>(new ArrayList<CommunityItem>());
        requestAllRecommendations();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.single_community_list_layout, null, false);
    }

    @Override
    public void onViewCreated(View pageView) {
        super.onViewCreated(pageView);
        appBarLayout = (AppBarLayout) pageView.findViewById(R.id.appbar_layout);
        recommendListView = (RecyclerView) pageView.findViewById(R.id.community_list);

        toolbar.setTitle(R.string.drawer_recommendations);
        prepareItemsViews(pageView);
    }

    private void prepareItemsViews(View pageView) {
        recommendAdapter = new GroupAdapter(recommendItems, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recommendListView.getChildAdapterPosition(v);

                Page detailPage = new GroupDetailPage(recommendItems.get(position).getCommunity(), null);
                pagerListener.openPage(detailPage);
            }
        });

        recommendListView.setLayoutManager(new LinearLayoutManager(pageView.getContext()));
        recommendListView.setAdapter(recommendAdapter);
    }

    private void requestAllRecommendations() {
        //TODO move it
        VKRequest vkRequest = VKApi.groups().getCatalog(null);
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
                                CommunityItem item = new MyCommunityItem(group);
                                recommendItems.add(item);
                                recommendAdapter.notifyItemInserted(recommendItems.size() - 1);
                            }
                        }
                    });
                } else {
                    VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                    GroupDao groupDao = new GroupDao();
                    for (final VKApiCommunityFull group : communityArray) {
                        groupDao.saveGroup(group);
                        CommunityItem item = new MyCommunityItem(group);
                        recommendItems.add(item);
                        recommendAdapter.notifyItemInserted(recommendItems.size() - 1);
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
}
