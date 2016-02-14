package com.github.programmerr47.vkgroups.adapter.item;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.github.programmerr47.vkgroups.DateFormatter;
import com.github.programmerr47.vkgroups.PhotoUtil;
import com.github.programmerr47.vkgroups.PostDescription;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.PostAttachmentSubHolder;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AudioAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.DocAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.LinkAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.MapAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.NoteAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PollAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PostAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.SimpleAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.WikiPageAttachmentItem;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiDocument;
import com.vk.sdk.api.model.VKApiLink;
import com.vk.sdk.api.model.VKApiNote;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiPoll;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiWikiPage;
import com.vk.sdk.api.model.VKAttachments.Type;
import com.vk.sdk.api.model.VKAttachments.VKApiAttachment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;
import static com.github.programmerr47.vkgroups.ViewUtils.setCommonMargin;
import static com.vk.sdk.api.model.VKAttachments.Type.AUDIO;
import static com.vk.sdk.api.model.VKAttachments.Type.DOC;
import static com.vk.sdk.api.model.VKAttachments.Type.LINK;
import static com.vk.sdk.api.model.VKAttachments.Type.NOTE;
import static com.vk.sdk.api.model.VKAttachments.Type.PHOTO;
import static com.vk.sdk.api.model.VKAttachments.Type.POLL;
import static com.vk.sdk.api.model.VKAttachments.Type.POST;
import static com.vk.sdk.api.model.VKAttachments.Type.POSTED_PHOTO;
import static com.vk.sdk.api.model.VKAttachments.Type.WIKI_PAGE;

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
    private List<LayoutParams> photosParams;
    private List<VKApiPhotoSize> photoSizes;

    private List<PostDescription> historyDescriptions;

    private List<AttachmentItem> attItems;

    public PostItem(VKApiPost vkApiPost, Map<Integer, VKApiUser> userMap, Map<Integer, VKApiCommunity> groupMap, PostItemNotifier notifier) {
        this.post = vkApiPost;
        this.userMap = userMap;
        this.groupMap = groupMap;
        this.notifier = notifier;
        initAllDescriptions();
        initAttachments(post);
        this.photosParams = initLayoutParams(attPhotos);
        this.photoSizes = initPhotoSizes(attPhotos);
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

        if (post.from_id > 0) {
            VKApiUser user = userMap.get(post.from_id);
            viewHolder.getOwnerTitleView().setText(String.format("%s %s", user.last_name, user.first_name));
            getImageWorker().loadImage(
                    user.photo_100,
                    viewHolder.getOwnerIconView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        } else {
            VKApiCommunity group = groupMap.get(Math.abs(post.from_id));
            viewHolder.getOwnerTitleView().setText(group.name);
            getImageWorker().loadImage(
                    group.photo_100,
                    viewHolder.getOwnerIconView(),
                    new ImageWorker.LoadBitmapParams(100, 100));
        }

        viewHolder.getOwnerDateView().setText(DateFormatter.formatDate(post.date));
        postDescription.appendToDescriptionTextView(viewHolder.getPostTextView());
        postDescription.appendToExpandCollapseTextView(viewHolder.getPostExpandCollapseView());

        for (int i = 0; i < viewHolder.getAttHolders().size(); i++) {
            if (i < attItems.size()) {
                PostAttachmentSubHolder attHolder = viewHolder.getAttHolders().get(i);
                attHolder.getHolderView().setVisibility(VISIBLE);
                attItems.get(i).bindView(attHolder);
            } else {
                viewHolder.getAttHolders().get(i).getHolderView().setVisibility(GONE);
            }
        }

        List<ImageView> photos = enableAppropriatePhotoList(viewHolder);

        for (int i = 0; i < attPhotos.size(); i++) {
            VKApiPhotoSize photoSize = photoSizes.get(i);
            ImageView photoView = photos.get(i);

            if (i < photosParams.size()) {
                photoView.setLayoutParams(photosParams.get(i));
            }

            Picasso.with(getAppContext()).load(photoSize.src).into(photoView);
        }
    }

    public static PostItemHolder createHolder(ViewGroup parentView, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
        ViewGroup baseView = (ViewGroup) layoutInflater.inflate(R.layout.post_item, parentView, false);

        if (baseView == null) {
            throw new IllegalStateException("View not created");
        }

        List<View> photosContainers = new ArrayList<>();
        List<List<ImageView>> photos = new ArrayList<>();

        View photoContainer = baseView.findViewById(R.id.attachment_photo_1);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1));

        photoContainer = baseView.findViewById(R.id.attachment_photo_2);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3));

        photoContainer = baseView.findViewById(R.id.attachment_photo_3);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3));

        photoContainer = baseView.findViewById(R.id.attachment_photo_4);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4));

        photoContainer = baseView.findViewById(R.id.attachment_photo_5);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5));

        photoContainer = baseView.findViewById(R.id.attachment_photo_6);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5, R.id.photo_6));

        photoContainer = baseView.findViewById(R.id.attachment_photo_7);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                R.id.photo_7));

        photoContainer = baseView.findViewById(R.id.attachment_photo_8);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                R.id.photo_7, R.id.photo_8));

        photoContainer = baseView.findViewById(R.id.attachment_photo_9);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                R.id.photo_7, R.id.photo_8, R.id.photo_9));

        photoContainer = baseView.findViewById(R.id.attachment_photo_10);
        photosContainers.add(photoContainer);
        photos.add(getPhotos(photoContainer,
                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                R.id.photo_7, R.id.photo_8, R.id.photo_9,
                R.id.photo_10));

        List<PostAttachmentSubHolder> attHolders = createAttHolders(baseView,
                R.id.attachment_1, R.id.attachment_2, R.id.attachment_3,
                R.id.attachment_4, R.id.attachment_5, R.id.attachment_6,
                R.id.attachment_7, R.id.attachment_8, R.id.attachment_9,
                R.id.attachment_10);

        PostItemHolder.ResourceParams params = new PostItemHolder.ResourceParams();
        params.ownerIconId = R.id.owner_icon;
        params.ownerTitleId = R.id.owner_title;
        params.ownerDateId = R.id.owner_date;
        params.postTextId = R.id.post_text;
        params.postExpandCollapseId = R.id.post_expand_collapse;
        params.likeActionId = R.id.like_action;
        params.repostActionId = R.id.share_post_action;
        params.commentActionId = R.id.comment_action;
        params.likeIconId = R.id.like_image;
        params.repostIconId = R.id.share_post_image;
        params.commentIconId = R.id.comment_image;
        params.likeCountId = R.id.like_count;
        params.repostCountId = R.id.share_post_count;
        params.commentCountId = R.id.comment_count;

        return new PostItemHolder(baseView, attHolders, photosContainers, photos, params);
    }

    private static List<PostAttachmentSubHolder> createAttHolders(View parent, int... ids) {
        List<PostAttachmentSubHolder> result = new ArrayList<>();

        for (int id : ids) {
            View attachment = parent.findViewById(id);
            result.add(createAttHolder(attachment));
        }

        return result;
    }

    private static PostAttachmentSubHolder createAttHolder(View attachment) {
        PostAttachmentSubHolder.ResourceParams params = new PostAttachmentSubHolder.ResourceParams();
        params.iconId = R.id.icon;
        params.titleId = R.id.title;
        params.subtitleId = R.id.subtitle;
        params.optionalInfoId = R.id.optional_info;

        return new PostAttachmentSubHolder(attachment, params);
    }

    public int getItemType() {
        return 1;
    }

    public VKApiPost getPost() {
        return post;
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

        attItems = new ArrayList<>();
        for (VKApiAttachment attachment : post.attachments) {
            if (isAttachmentOneOf(attachment, AUDIO, DOC, LINK, NOTE, POLL, POST, WIKI_PAGE)) {
                attItems.add(createAttItem(attachment));
            }
        }

        if (post.geo != null) {
            attItems.add(new MapAttachmentItem(post.geo));
        }
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

    private boolean isAttachmentPhoto(VKApiAttachment attachment) {
        return isAttachmentOneOf(attachment, PHOTO, POSTED_PHOTO);
    }

    private boolean isAttachmentOneOf(VKApiAttachment attachment, Type... types) {
        for (Type type : types) {
            if (type == attachment.getType()) {
                return true;
            }
        }

        return false;
    }

    private static List<ImageView> getPhotos(View sourceView, int... ids) {
        List<ImageView> result = new ArrayList<>();
        for (int id : ids) {
            result.add((ImageView) sourceView.findViewById(id));
        }
        return result;
    }

    private AttachmentItem createAttItem(VKApiAttachment apiAttachment) {
        switch (apiAttachment.getType()) {
            case AUDIO:
                return new AudioAttachmentItem((VKApiAudio) apiAttachment);
            case DOC:
                return new DocAttachmentItem((VKApiDocument) apiAttachment);
            case LINK:
                return new LinkAttachmentItem((VKApiLink) apiAttachment);
            case NOTE:
                return new NoteAttachmentItem((VKApiNote) apiAttachment);
            case POLL:
                return new PollAttachmentItem((VKApiPoll) apiAttachment);
            case POST:
                return new PostAttachmentItem((VKApiPost) apiAttachment);
            case WIKI_PAGE:
                return new WikiPageAttachmentItem((VKApiWikiPage) apiAttachment);
            default:
                return new SimpleAttachmentItem(apiAttachment);
        }
    }

    private List<LayoutParams> initLayoutParams(List<VKApiPhoto> photos) {
        if (photos.size() != 1) {
            return Collections.emptyList();
        } else {
            List<LayoutParams> paramsList = new ArrayList<>();
            VKApiPhotoSize minPhotoSize = PhotoUtil.getMinPhotoSizeForPost(photos.get(0).src);
            Pair<Integer, Integer> dimensForPhoto = PhotoUtil.getPostPhotoDimensions(minPhotoSize);
            LayoutParams layoutParams = new LayoutParams(dimensForPhoto.first, dimensForPhoto.second);
            setCommonMargin(layoutParams, res().dimenI(R.dimen.margin_medium));
            paramsList.add(layoutParams);
            return paramsList;
        }
    }

    private List<VKApiPhotoSize> initPhotoSizes(List<VKApiPhoto> photos) {
        switch (photos.size()) {
            case 1:
                return initPhotoSizesForOne(photos);
            case 2:
                return initPhotoSizesForFixed(photos, 2);
            case 3:
                return initPhotoSizesForThree(photos);
            case 4:
                return initPhotoSizesForFixed(photos, 2);
            case 5:
                return initPhotoSizesForFive(photos);
            case 6:
                return initPhotoSizesForSix(photos);
            case 7:
                return initPhotoSizesForSeven(photos);
            case 8:
                return initPhotoSizesForFixed(photos, 4);
            case 9:
                return initPhotoSizesForNine(photos);
            case 10:
                return initPhotoSizesForTen(photos);
            default:
                return initPhotoSizesForFixed(photos, 4);
        }
    }

    private List<VKApiPhotoSize> initPhotoSizesForOne(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        VKApiPhotoSize minPhotoSize = PhotoUtil.getMinPhotoSizeForPost(photos.get(0).src);
        photoSizeList.add(minPhotoSize);
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForFixed(List<VKApiPhoto> photos, float fixed) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        for (VKApiPhoto photo : photos) {
            photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photo.src, fixed));
        }
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForThree(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 3 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 3));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForFive(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 5 / 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 5 / 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 5 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).src, 5 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).src, 5 / 2));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForSix(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).src, 4));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForSeven(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).src, 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).src, 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).src, 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).src, 5));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForNine(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).src, 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).src, 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(7).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(8).src, 4));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForTen(List<VKApiPhoto> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).src, 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(7).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(8).src, 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(9).src, 4));
        return photoSizeList;
    }

    private PostAttachmentSubHolder createAttachmentSubHolder(View attachmentView) {
        PostAttachmentSubHolder.ResourceParams params = new PostAttachmentSubHolder.ResourceParams();
        params.iconId = R.id.icon;
        params.titleId = R.id.title;
        params.subtitleId = R.id.subtitle;
        params.optionalInfoId = R.id.optional_info;

        return new PostAttachmentSubHolder(attachmentView, params);
    }

    private List<ImageView> enableAppropriatePhotoList(PostItemHolder holder) {
        for (int i = 0; i < holder.getPhotoContainers().size(); i++) {
            if (i == attPhotos.size() - 1) {
                holder.getPhotoContainers().get(i).setVisibility(VISIBLE);
            } else {
                holder.getPhotoContainers().get(i).setVisibility(GONE);
            }
        }

        if (attPhotos.size() == 0) {
            return Collections.emptyList();
        } else {
            return holder.getPhotos().get(attPhotos.size() - 1);
        }
    }
}
