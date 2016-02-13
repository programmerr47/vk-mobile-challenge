package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.AndroidUtils;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.background.methods.VkApiWallWrapper;
import com.github.programmerr47.vkgroups.collections.PostItems;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.vk.sdk.api.model.VKApiCommunity;

import java.util.ArrayList;
import java.util.List;

import static com.github.programmerr47.vkgroups.AndroidUtils.pxF;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class GroupDetailPage extends Page {

    private final VKApiCommunity community;

    private ImageView groupImage;
    private RecyclerView postListView;
    private PostAdapter postAdapter;
    private PostItems items;

    private final List<Runnable> uiWorks = new ArrayList<>();

    public GroupDetailPage(VKApiCommunity community) {
        this.community = community;

        items = new PostItems(new ArrayList<PostItem>());
        postAdapter = new PostAdapter(items);
        VkApiWallWrapper.getFromOwner(-community.id, postAdapter, new VkApiWallWrapper.GetPostsListener() {
            @Override
            public void onPostsLoaded(final PostItems posts, int offset, int count) {

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
        postListView.setLayoutManager(new LinearLayoutManager(postListView.getContext()));

        getImageWorker().loadImage(
                community.photo_200,
                groupImage,
                new ImageWorker.LoadBitmapParams(200, 200, false));

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
