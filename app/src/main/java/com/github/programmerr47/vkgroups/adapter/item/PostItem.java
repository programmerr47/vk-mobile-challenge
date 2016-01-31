package com.github.programmerr47.vkgroups.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.DateFormatter;
import com.github.programmerr47.vkgroups.PostDescription;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.VKGroupApplication;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.PostItemHolderProducer;
import com.github.programmerr47.vkgroups.background.objects.special.Attachment;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.github.programmerr47.vkgroups.view.PostContentView;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKAttachments.VKApiAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;
import static com.vk.sdk.api.model.VKAttachments.TYPE_ALBUM;
import static com.vk.sdk.api.model.VKAttachments.TYPE_PHOTO;
import static com.vk.sdk.api.model.VKAttachments.TYPE_POSTED_PHOTO;
import static com.vk.sdk.api.model.VKAttachments.TYPE_VIDEO;

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
    private List<VKApiPhoto> attPhotos;
    private List<VKApiAudio> attAudios;

    private List<PostDescription> historyDescriptions;

    public PostItem(VKApiPost vkApiPost, Map<Integer, VKApiUser> userMap, Map<Integer, VKApiCommunity> groupMap, PostItemNotifier notifier) {
        this.post = vkApiPost;
        this.userMap = userMap;
        this.groupMap = groupMap;
        this.notifier = notifier;
        initAllDescriptions();
        initAttachments(post);
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
            viewHolder.getLikeCountView().setVisibility(VISIBLE);
        } else {
            viewHolder.getLikeCountView().setVisibility(GONE);
        }

        if (post.reposts_count > 0) {
            viewHolder.getRepostCountView().setText(String.valueOf(post.reposts_count));
            viewHolder.getRepostCountView().setVisibility(VISIBLE);
        } else {
            viewHolder.getRepostCountView().setVisibility(GONE);
        }

        if (post.comments_count > 0) {
            viewHolder.getCommentCountView().setText(String.valueOf(post.comments_count));
            viewHolder.getCommentCountView().setVisibility(VISIBLE);
            viewHolder.getCommentActionView().setVisibility(VISIBLE);
        } else {
            if (post.can_post_comment) {
                viewHolder.getCommentCountView().setVisibility(GONE);
            } else {
                viewHolder.getCommentActionView().setVisibility(GONE);
            }
        }

        PostContentView ownerContentView = viewHolder.getOwnerContentView();
        if (post.from_id > 0) {
            VKApiUser user = userMap.get(post.from_id);
            ownerContentView.getOwnerTitleView().setText(String.format("%s %s", user.last_name, user.first_name));
            getImageWorker().loadImage(
                    user.photo_100,
                    ownerContentView.getOwnerImageView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        } else {
            VKApiCommunity group = groupMap.get(Math.abs(post.from_id));
            ownerContentView.getOwnerTitleView().setText(group.name);
            getImageWorker().loadImage(
                    group.photo_100,
                    ownerContentView.getOwnerImageView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        }

        ownerContentView.getOwnerPostDateView().setText(DateFormatter.formatDate(post.date));
        postDescription.appendToDescriptionTextView(ownerContentView.getPostTextView());
        postDescription.appendToExpandCollapseTextView(ownerContentView.getPostExpandCollapseView());

        for (int i = 0; i < attPhotos.size(); i++) {
            VKApiPhoto photo = attPhotos.get(i);
            VKApiPhotoSize photoSize = photo.src.get(photo.src.size() - 1);

            Picasso.with(getAppContext()).load(photoSize.src).into(ownerContentView.getPhotos().get(i));
        }
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

                List<ImageView> photos = new ArrayList<>();
                if (attPhotos.size() != 9) {
                    for (VKApiPhoto attPhoto : attPhotos) {
                        View view = layoutInflater.inflate(R.layout.attachment_photo, ownerPostContent, true);
                        ImageView photo = (ImageView) view.findViewById(R.id.photo_1);
                        photos.add(photo);
                    }
                } else {
                    View view = layoutInflater.inflate(R.layout.attachment_photo_9, ownerPostContent, true);
                    getPhotos(view, photos,
                            R.id.photo_1, R.id.photo_2, R.id.photo_3,
                            R.id.photo_4, R.id.photo_5, R.id.photo_6,
                            R.id.photo_7, R.id.photo_8, R.id.photo_9);
                }

                for (VKApiAudio attAudio : attAudios) {
                    layoutInflater.inflate(R.layout.attachment_audio, ownerPostContent, true);
                }

                ownerPostContent.setPhotos(photos);
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
        return "p:" + attPhotos.size() + ";a:" + attAudios.size();
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

    private void initAttachments(VKApiPost post) {
        attPhotos = getAttachments(post, VKApiPhoto.class);
        attAudios = getAttachments(post, VKApiAudio.class);
    }

    private <T> List<T> getAttachments(VKApiPost post, Class<T> attachmentType) {
        List<T> result = new ArrayList<>();

        for (VKApiAttachment attachment : post.attachments) {
            if (attachmentType.isInstance(attachment)) {
                result.add(attachmentType.cast(attachment));
            }
        }

        return result;
    }

    private List<VKApiPhoto> getPhotos(VKApiPost post) {
        List<VKApiPhoto> result = new ArrayList<>();

        for (VKApiAttachment attachment : post.attachments) {
            if (isAttachmentPhoto(attachment)) {
                result.add((VKApiPhoto)attachment);
            }
        }

        return result;
    }

    private boolean isAttachmentPhoto(VKApiAttachment attachment) {
        return isAttachmentOnOf(attachment, TYPE_PHOTO, TYPE_POSTED_PHOTO);
    }

    private boolean isAttachmentOnOf(VKApiAttachment attachment, String... types) {
        for (String type : types) {
            if (type.equals(attachment.getType())) {
                return true;
            }
        }

        return false;
    }

    private void getPhotos(View sourceView, List<ImageView> targetList, int... ids) {
        for (int id : ids) {
            targetList.add((ImageView) sourceView.findViewById(id));
        }
    }
}
