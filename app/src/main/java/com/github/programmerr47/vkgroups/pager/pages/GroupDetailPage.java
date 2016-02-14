package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.background.methods.VkApiWallWrapper;
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
        VkApiWallWrapper.getFromOwner(-community.id, postAdapter, new VkApiWallWrapper.GetPostsListener() {
            @Override
            public void onPostsLoaded(final List<PostItem> posts, int offset, int count) {
                if (posts != null) {
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

        postListView.setRecycledViewPool(postViewPool);
        postListView.setAdapter(postAdapter);
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
