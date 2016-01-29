package com.github.programmerr47.vkgroups.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.DateFormatter;
import com.github.programmerr47.vkgroups.PostDescription;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.PostItemHolderProducer;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.github.programmerr47.vkgroups.view.PostContentView;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-24
 */
public final class PostItem implements PostDescription.OnDescriptionRepresentationChangedListener {

    private VKApiPost post;
    private Map<Integer, VKApiUser> userMap;
    private Map<Integer, VKApiCommunity> groupMap;

    private PostItemNotifier notifier;

    private PostDescription postDescription;
    private List<PostDescription> historyDescriptions;

    public PostItem(VKApiPost vkApiPost, Map<Integer, VKApiUser> userMap, Map<Integer, VKApiCommunity> groupMap, PostItemNotifier notifier) {
        this.post = vkApiPost;
        this.userMap = userMap;
        this.groupMap = groupMap;
        this.notifier = notifier;
        initAllDescriptions();
    }

    @Override
    public void onDescriptionChanged(boolean isCollapsed) {
        notifier.notifyItemChanged(this);
    }

    public void bindView(PostItemHolder viewHolder, int position) {
        if (post.user_likes) {
            viewHolder.getLikeIconView().setImageResource(R.drawable.ic_heart_grey600_36dp);
        } else {
            viewHolder.getLikeIconView().setImageResource(R.drawable.ic_heart_outline_grey600_36dp);
        }

        if (post.likes_count > 0) {
            viewHolder.getLikeCountView().setText(String.valueOf(post.likes_count));
            viewHolder.getLikeCountView().setVisibility(View.VISIBLE);
        } else {
            viewHolder.getLikeCountView().setVisibility(View.GONE);
        }

        if (post.reposts_count > 0) {
            viewHolder.getRepostCountView().setText(String.valueOf(post.reposts_count));
            viewHolder.getRepostCountView().setVisibility(View.VISIBLE);
        } else {
            viewHolder.getRepostCountView().setVisibility(View.GONE);
        }

        if (post.comments_count > 0) {
            viewHolder.getCommentCountView().setText(String.valueOf(post.comments_count));
            viewHolder.getCommentCountView().setVisibility(View.VISIBLE);
            viewHolder.getCommentActionView().setVisibility(View.VISIBLE);
        } else {
            if (post.can_post_comment) {
                viewHolder.getCommentCountView().setVisibility(View.GONE);
            } else {
                viewHolder.getCommentActionView().setVisibility(View.GONE);
            }
        }

        if (post.from_id > 0) {
            VKApiUser user = userMap.get(post.from_id);
            viewHolder.getOwnerContentView().getOwnerTitleView().setText(String.format("%s %s", user.last_name, user.first_name));
            getImageWorker().loadImage(
                    user.photo_100,
                    viewHolder.getOwnerContentView().getOwnerImageView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        } else {
            VKApiCommunity group = groupMap.get(Math.abs(post.from_id));
            viewHolder.getOwnerContentView().getOwnerTitleView().setText(group.name);
            getImageWorker().loadImage(
                    group.photo_100,
                    viewHolder.getOwnerContentView().getOwnerImageView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        }

        viewHolder.getOwnerContentView().getOwnerPostDateView().setText(DateFormatter.formatDate(post.date));
        postDescription.appendToTextView(viewHolder.getOwnerContentView().getPostTextView());
    }

    public PostItemHolderProducer getViewHolderProducer() {
        return new PostItemHolderProducer() {
            @Override
            public PostItemHolder produce(ViewGroup parentView) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
                ViewGroup baseView = (ViewGroup) layoutInflater.inflate(R.layout.post_item, parentView, false);

                if (baseView == null) {
                    throw new IllegalStateException("View not created");
                }

                LinearLayout postSections = (LinearLayout) baseView.getChildAt(0);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                PostContentView ownerPostContent = new PostContentView(postSections.getContext());
                ownerPostContent.setLayoutParams(layoutParams);
                postSections.addView(ownerPostContent, 0);

                PostItemHolder.ResourceParams params = new PostItemHolder.ResourceParams();
                params.likeActionId = R.id.like_action;
                params.repostActionId = R.id.share_post_action;
                params.commentActionId = R.id.comment_action;
                params.likeIconId = R.id.like_image;
                params.repostIconId = R.id.share_post_image;
                params.commentIconId = R.id.comment_image;
                params.likeCountId = R.id.like_count;
                params.repostCountId = R.id.share_post_count;
                params.commentCountId = R.id.comment_count;

                return new PostItemHolder(baseView, ownerPostContent, params);
            }
        };
    }

    public String getItemId() {
        return "test";
    }

    private void initAllDescriptions() {
        postDescription = createDescription(post);

        historyDescriptions = new ArrayList<>();
        for (VKApiPost historyPost : post.copy_history) {
            historyDescriptions.add(createDescription(historyPost));
        }
    }

    private PostDescription createDescription(VKApiPost post) {
        return new PostDescription(post.text, this);
    }
}
