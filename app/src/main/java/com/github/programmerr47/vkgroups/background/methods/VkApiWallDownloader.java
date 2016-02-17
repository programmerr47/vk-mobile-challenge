package com.github.programmerr47.vkgroups.background.methods;

import android.util.Log;

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
 * @author Mihail Spitsin
 * @since 2/17/2016
 */
public class VkApiWallDownloader {

    private static final Executor REQUEST_EXECUTOR = Executors.newSingleThreadExecutor();

//    private boolean isReady = true;
    private boolean isPostsDownloading;
    private boolean isPostsDownloadedCompletely;
//    private boolean isKnownAboutDownloading;

    private PostItemNotifier postItemNotifier;
    private GetPostsListener postsListener;

    public void download(int id) {
        download(id, 0);
    }

    public void download(int id, int offset) {
        download(id, offset, 20);
    }

    public void download(int id, final int offset, final int count) {
        if (!isPostsDownloading) {
            isPostsDownloading = true;
//            isKnownAboutDownloading = false;

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

                            if (postArray.size() < count) {

                                VKGroupApplication.getUiHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        isPostsDownloadedCompletely = true;
                                    }
                                });
                            }

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
                                PostItem postItem = new PostItem(apiPost, userMap, groupMap, postItemNotifier);
                                items.add(postItem);
                            }

                            VKGroupApplication.getUiHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    postsListener.onPostsLoaded(items, offset, count);
                                    isPostsDownloading = false;
                                }
                            });
                        }
                    });
                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                    super.attemptFailed(request, attemptNumber, totalAttempts);
                    isPostsDownloading = false;
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                    isPostsDownloading = false;
                }

                @Override
                public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                    super.onProgress(progressType, bytesLoaded, bytesTotal);
                }
            });
        }
    }

    public void setItemNotifier(PostItemNotifier postItemNotifier) {
        this.postItemNotifier = postItemNotifier;
    }

    public void setPostsListener(GetPostsListener postsListener) {
        this.postsListener = postsListener;
    }

//    public void setReadyState(boolean isReady) {
//        this.isReady = isReady;
//
//        if (isReady && !isPostsDownloading && !isKnownAboutDownloading) {
//            performCompletionOfDownloadingPart();
//        }
//    }

    public boolean isPostsDownloadedCompletely() {
        return isPostsDownloadedCompletely;
    }

    public boolean canDownload() {
        return !isPostsDownloadedCompletely && !isPostsDownloading;
    }

    public void setChatsDownloadedCompletely() {
        this.isPostsDownloadedCompletely = true;
    }

//    private void performCompletionOfDownloadingPart() {
//        onPartIsDownloaded();
//        isKnownAboutDownloading = true;
//    }

    public interface GetPostsListener {
        void onPostsLoaded(List<PostItem> posts, int offset, int count);
    }
}
