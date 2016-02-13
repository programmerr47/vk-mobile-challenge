package com.github.programmerr47.vkgroups.background.methods;

import android.util.LruCache;

import com.github.programmerr47.vkgroups.VKGroupApplication;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.adapter.item.PostItemNotifier;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Michael Spitsin
 * @since 2016-02-13
 */
public class VkApiWallWrapper {

    private static final Executor REQUEST_EXECUTOR = Executors.newSingleThreadExecutor();

    private static final LruCache<Integer, List<PostItem>> postsCache = new LruCache<>(100);

    public static List<PostItem> getFromOwner(int id, PostItemNotifier itemNotifier, GetPostsListener listener) {
        return getFromOwner(id, 0, itemNotifier, listener);
    }

    public static List<PostItem> getFromOwner(int id, int offset, PostItemNotifier itemNotifier, GetPostsListener listener) {
        return getFromOwner(id, offset, 20, itemNotifier, listener);
    }

    public static List<PostItem> getFromOwner(final int id, final int offset, final int count, final PostItemNotifier itemNotifier, final GetPostsListener listener) {
        final VKRequest request = VKApi.wall().get(VKParameters.from(
                VKApiConst.OWNER_ID, id,
                VKApiConst.EXTENDED, 1,
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                REQUEST_EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        VKPostArray postArray = (VKPostArray) response.parsedModel;

                        Map<Integer, VKApiUser> userMap = new HashMap<>();
                        for (VKApiUser user : postArray.getUsers()) {
                            userMap.put(user.id, user);
                        }

                        Map<Integer, VKApiCommunity> groupMap = new HashMap<>();
                        for (VKApiCommunity group : postArray.getCommunities()) {
                            groupMap.put(group.id, group);
                        }

                        final List<PostItem> items = new ArrayList<>();
                        for (VKApiPost apiPost : postArray) {
                            PostItem postItem = new PostItem(apiPost, userMap, groupMap, itemNotifier);
                            items.add(postItem);
                        }

                        VKGroupApplication.getUiHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onPostsLoaded(items, offset, count);
                            }
                        });
                    }
                });
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

        return null;
    }

    public interface GetPostsListener {
        void onPostsLoaded(List<PostItem> posts, int offset, int count);
    }
}
