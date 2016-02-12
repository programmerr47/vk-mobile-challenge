package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.collections.PostItems;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKPostArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class GroupDetailPage extends Page {

    private final VKApiCommunity community;

    private ImageView groupImage;
    private RecyclerView postListView;
    private PostItems items;

    public GroupDetailPage(VKApiCommunity community) {
        this.community = community;

        loadPosts();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.group_page, null, false);
    }

    @Override
    public void onViewCreated(View attachedView) {
        groupImage = (ImageView) attachedView.findViewById(R.id.group_image);
        postListView = (RecyclerView) attachedView.findViewById(R.id.post_list);
        postListView.setLayoutManager(new LinearLayoutManager(postListView.getContext()));

        getImageWorker().loadImage(
                community.photo_200,
                groupImage,
                new ImageWorker.LoadBitmapParams(200, 200, false));
    }

    //TODO it for test
    private void loadPosts() {
        final VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -community.id, VKApiConst.EXTENDED, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKPostArray postArray = (VKPostArray) response.parsedModel;

                Map<Integer, VKApiUser> userMap = new HashMap<>();
                for (VKApiUser user : postArray.getUsers()) {
                    userMap.put(user.id, user);
                }

                Map<Integer, VKApiCommunity> groupMap = new HashMap<>();
                for (VKApiCommunity group : postArray.getCommunities()) {
                    groupMap.put(group.id, group);
                }

                items = new PostItems(new ArrayList<PostItem>());
                PostAdapter adapter = new PostAdapter(items);
                for (VKApiPost apiPost : postArray) {
                    PostItem postItem = new PostItem(apiPost, userMap, groupMap, adapter);
                    items.add(postItem);
                }

                postListView.setAdapter(adapter);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
}
