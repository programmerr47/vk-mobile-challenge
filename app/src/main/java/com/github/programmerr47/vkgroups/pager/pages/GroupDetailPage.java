package com.github.programmerr47.vkgroups.pager.pages;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.collections.PostItems;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKPostArray;

import java.util.ArrayList;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class GroupDetailPage {

    private int id;

    private View attachedView;

    private RecyclerView postListView;

    public GroupDetailPage(Context context, int id){
        this.id = id;
        attachedView = getViewByLayoutId(context, R.layout.group_page);
        onViewCreated(attachedView);
    }

    private View getViewByLayoutId(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    private void onViewCreated(View attachedView) {
        postListView = (RecyclerView) attachedView.findViewById(R.id.post_list);
        postListView.setLayoutManager(new LinearLayoutManager(postListView.getContext()));

        loadPosts();
    }

    public View getAttachedView() {
        return attachedView;
    }

    //TODO it for test
    private void loadPosts() {
        final VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, id, VKApiConst.EXTENDED, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKPostArray postArray = (VKPostArray) response.parsedModel;
                PostItems items = new PostItems(new ArrayList<PostItem>());
                for (VKApiPost apiPost : postArray) {
                    PostItem postItem = new PostItem(apiPost);
                    items.add(postItem);
                }

                PostAdapter adapter = new PostAdapter(items);
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
