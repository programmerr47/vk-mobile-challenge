package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.background.methods.VkApiWallDownloader;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.vk.sdk.api.model.VKApiCommunity;

import java.util.ArrayList;
import java.util.List;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class GroupDetailPage extends Page {

    private final VKApiCommunity community;
    private final VkApiWallDownloader wallDownloader = new VkApiWallDownloader();

    private ImageView groupImage;
    RecyclerView.RecycledViewPool postViewPool;
    private RecyclerView postListView;
    private PostAdapter postAdapter;
    private List<PostItem> items;

    private final List<Runnable> uiWorks = new ArrayList<>();

    public GroupDetailPage(VKApiCommunity community, RecyclerView.RecycledViewPool postViewPool) {
        this.community = community;
        this.postViewPool = postViewPool;

        items = new ArrayList<>();
        postAdapter = new PostAdapter(items);
        wallDownloader.setItemNotifier(postAdapter);
        wallDownloader.setPostsListener(new VkApiWallDownloader.GetPostsListener() {
            @Override
            public void onPostsLoaded(final List<PostItem> posts, int offset, int count) {
                Log.v("FUCK", "Do I downloaded posts? " + (posts != null));
                if (posts != null) {
                    Log.v("FUCK", "Downloaded " + posts.size() + " items with requested offset: " + offset + ", count: " + count);
                    if (isTransitionAnimating) {
                        uiWorks.add(new Runnable() {
                            @Override
                            public void run() {
                                postAdapter.addItems(posts);
                            }
                        });
                    } else {
                        postAdapter.addItems(posts);
                    }
                }
            }
        });
        wallDownloader.download(-community.id);
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

        final LinearLayoutManager postLayoutManager = new LinearLayoutManager(postListView.getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                int originResult = super.getExtraLayoutSpace(state);
                return originResult != 0 ? originResult : OrientationHelper.createOrientationHelper(this, getOrientation()).getTotalSpace();
            }
        };
        postListView.setLayoutManager(postLayoutManager);

        getImageWorker().loadImage(
                community.photo_200,
                groupImage,
                new ImageWorker.LoadBitmapParams(200, 200, false));

//        postListView.setRecycledViewPool(postViewPool);
        postListView.setAdapter(postAdapter);
        postListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.v("FUCK", "scrolled, can I download now? " + wallDownloader.canDownload() + " .My dy = " + dy);
                if (wallDownloader.canDownload() && dy > 0) {
                    Log.v("FUCK", "I can download, dy = " + dy);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int count = 2 * (manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition() + 1);
                    Log.v("FUCK", "Have: " + items.size() + " items, next is: " + (manager.findLastVisibleItemPosition() + count) + ", window size: " + count);
                    if (items.size() < manager.findLastVisibleItemPosition() + count) {
//                        if (!mChatListRecyclerView.getLoadingState()) {
//                            mChatListItems.add(mLoadingItem);
//                            mChatListAdapter.notifyDataSetChanged();
//                        }

                        Log.v("FUCK", "Download!");
                        wallDownloader.download(-community.id, items.size());
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        executeAllWorks();
    }

    private void executeAllWorks() {
        if (uiWorks.size() > 0) {
            for (Runnable runnable : uiWorks) {
                runnable.run();
            }

            uiWorks.clear();
        }
    }

}
