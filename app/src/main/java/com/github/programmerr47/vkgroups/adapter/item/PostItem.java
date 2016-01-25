package com.github.programmerr47.vkgroups.adapter.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.PostItemHolderProducer;
import com.github.programmerr47.vkgroups.view.PostContentView;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;

import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-01-24
 */
public final class PostItem {

    private VKApiPost post;
    private Map<String, VKApiUser> postUsers;
    private Map<String, VKApiCommunity> postCommunity;

    public void bindView(PostItemHolder viewHolder, int position) {

    }

    public PostItemHolderProducer getViewHolderProducer() {
        return new PostItemHolderProducer() {
            @Override
            public PostItemHolder produce(ViewGroup parentView) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
                LinearLayout baseView = (LinearLayout) layoutInflater.inflate(R.layout.post_item, parentView, false);

                if (baseView == null) {
                    throw new IllegalStateException("View not created");
                }

                PostContentView ownerPostContent = new PostContentView(baseView.getContext());
                baseView.addView(ownerPostContent, 0);

                PostItemHolder.ResourceParams params = new PostItemHolder.ResourceParams();
                params.likeIconId = R.id.like_image;
                params.repostIconId = R.id.share_post_image;
                params.commentIconId = R.id.comment_image;
                params.likeCountId = R.id.like_count;
                params.repostIconId = R.id.share_post_count;
                params.commentIconId = R.id.comment_count;

                return new PostItemHolder(baseView, ownerPostContent, params);
            }
        };
    }

    public String getItemId() {
        return "test";
    }
}
