package com.github.programmerr47.vkgroups.adapter.item;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.github.programmerr47.vkgroups.utils.DateFormatter;
import com.github.programmerr47.vkgroups.utils.PhotoUtil;
import com.github.programmerr47.vkgroups.utils.PostDescription;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.github.programmerr47.vkgroups.adapter.holder.PostAttachmentSubHolder;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AlbumAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AppAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AudioAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.AttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.DocAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.LinkAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.MapAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.NoteAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PhotoAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PhotoSizeAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PollAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.PostAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.SimpleAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.VideoAttachmentItem;
import com.github.programmerr47.vkgroups.adapter.item.subitems.WikiPageAttachmentItem;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.github.programmerr47.vkgroups.utils.PostIdConverter;
import com.vk.sdk.api.model.PhotoSizable;
import com.vk.sdk.api.model.VKApiApplicationContent;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiDocument;
import com.vk.sdk.api.model.VKApiLink;
import com.vk.sdk.api.model.VKApiNote;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiPoll;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiVideo;
import com.vk.sdk.api.model.VKApiWikiPage;
import com.vk.sdk.api.model.VKAttachments.VKApiAttachment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.utils.AndroidUtils.identifiers;
import static com.github.programmerr47.vkgroups.utils.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;
import static com.github.programmerr47.vkgroups.utils.AttachmentUtils.isAttachmentOneOf;
import static com.github.programmerr47.vkgroups.utils.AttachmentUtils.isAttachmentPhoto;
import static com.github.programmerr47.vkgroups.utils.PostIdConverter.idFromPost;
import static com.github.programmerr47.vkgroups.utils.PostIdConverter.postInfoFromId;
import static com.github.programmerr47.vkgroups.utils.ViewUtils.setCommonMargin;
import static com.vk.sdk.api.model.VKAttachments.Type.AUDIO;
import static com.vk.sdk.api.model.VKAttachments.Type.DOC;
import static com.vk.sdk.api.model.VKAttachments.Type.LINK;
import static com.vk.sdk.api.model.VKAttachments.Type.NOTE;
import static com.vk.sdk.api.model.VKAttachments.Type.POLL;
import static com.vk.sdk.api.model.VKAttachments.Type.POST;
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

    private List<PostDescription> historyDescriptions;

    private List<AttachmentItem> attItems;
    private List<PhotoSizeAttachmentItem> photoSizeAttItems;

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
            viewHolder.getLikeCountView().setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_grey600_36dp, 0, 0);
        } else {
            viewHolder.getLikeCountView().setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_outline_grey600_36dp, 0, 0);
        }

        if (post.likes_count > 0) {
            viewHolder.getLikeCountView().setText(String.valueOf(post.likes_count));
        } else {
            viewHolder.getLikeCountView().setText(null);
        }

        if (post.reposts_count > 0) {
            viewHolder.getRepostCountView().setText(String.valueOf(post.reposts_count));
        } else {
            viewHolder.getRepostCountView().setText(null);
        }

        if (post.comments_count > 0) {
            viewHolder.getCommentCountView().setText(String.valueOf(post.comments_count));
            viewHolder.getCommentCountView().setVisibility(VISIBLE);
        } else {
            if (post.can_post_comment) {
                viewHolder.getRepostCountView().setText(null);
            } else {
                viewHolder.getCommentCountView().setVisibility(GONE);
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
            attItems.get(i).bindView(viewHolder.getAttHolders().get(i));
        }

        for (int i = 0; i < viewHolder.getPhotoHolders().size(); i++) {
            photoSizeAttItems.get(i).bindView(viewHolder.getPhotoHolders().get(i));
        }
    }

    public static PostItemHolder createHolder(ViewGroup parentView, int viewType) {
        PostIdConverter.PostCounters postCounters = postInfoFromId(viewType);

        LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
        ViewGroup baseView = (ViewGroup) layoutInflater.inflate(R.layout.post_item, parentView, false);

        if (baseView == null) {
            throw new IllegalStateException("View not created");
        }

        LinearLayout postContent = (LinearLayout) baseView.findViewById(R.id.post_content);

        List<View> photosContainers = new ArrayList<>();
        List<PhotoSizeAttachmentSubHolder> photoHolders = createAttPhotoHolders(layoutInflater, postContent, postCounters.photoAttachments);

        PostItemHolder.ResourceParams params = new PostItemHolder.ResourceParams();
        params.ownerIconId = R.id.owner_icon;
        params.ownerTitleId = R.id.owner_title;
        params.ownerDateId = R.id.owner_date;
        params.postTextId = R.id.post_text;
        params.postExpandCollapseId = R.id.post_expand_collapse;
        params.likeCountId = R.id.like_count;
        params.repostCountId = R.id.share_post_count;
        params.commentCountId = R.id.comment_count;

        List<PostAttachmentSubHolder> attHolders = createAttHolders(layoutInflater, postContent, postCounters.otherAttachments);

        return new PostItemHolder(baseView, attHolders, photosContainers, photoHolders, params);
    }

    private static List<PhotoSizeAttachmentSubHolder> createAttPhotoHolders(LayoutInflater inflater, ViewGroup root, int photoCount) {
        if (photoCount > 0) {
            View photosView = inflater.inflate(identifiers().layout("attachment_photo_" + photoCount), root, false);
            root.addView(photosView, getAddIndex(root));
            int[] ids = new int[photoCount];

            for (int i = 0; i < photoCount; i++) {
                ids[i] = identifiers().id("photo_" + (i + 1));
            }

            return getPhotos(photosView, ids);
        } else {
            return Collections.emptyList();
        }
    }

    private static List<PostAttachmentSubHolder> createAttHolders(LayoutInflater inflater, ViewGroup root, int attCount) {
        if (attCount > 0) {
            List<PostAttachmentSubHolder> result = new ArrayList<>();

            int addIndex = getAddIndex(root);
            for (int i = 0; i < attCount; i++) {
                View attachment = inflater.inflate(R.layout.attachment, root, false);
                root.addView(attachment, addIndex + i);
                result.add(createAttHolder(attachment));
            }

            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private static PostAttachmentSubHolder createAttHolder(View attachment) {
        PostAttachmentSubHolder.ResourceParams params = new PostAttachmentSubHolder.ResourceParams();
        params.iconId = R.id.icon;
        params.titleId = R.id.title;
        params.subtitleId = R.id.subtitle;
        params.optionalInfoId = R.id.optional_info;

        return new PostAttachmentSubHolder(attachment, params);
    }

    private static int getAddIndex(ViewGroup postContentLayout) {
        return postContentLayout.indexOfChild(postContentLayout.findViewById(R.id.post_delimiter));
    }

    public int getItemType() {
        return idFromPost(post);
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
        List<PhotoSizable> photoSizeObjects = getAttachments(post, PhotoSizable.class);
        List<LayoutParams> photosParams = initLayoutParams(photoSizeObjects);
        List<VKApiPhotoSize> photoSizes = initPhotoSizes(photoSizeObjects);
        

        int photoAttIndex = 0;
        attItems = new ArrayList<>();
        photoSizeAttItems = new ArrayList<>();
        for (VKApiAttachment attachment : post.attachments) {
            if (isAttachmentOneOf(attachment, AUDIO, DOC, LINK, NOTE, POLL, POST, WIKI_PAGE)) {
                attItems.add(createAttItem(attachment));
            } else if (isAttachmentPhoto(attachment)) {
                LayoutParams params = photoAttIndex < photosParams.size() ? photosParams.get(photoAttIndex) : null;
                photoSizeAttItems.add(createPhotoSizeAttItem(attachment, photoSizes.get(photoAttIndex), params));
                photoAttIndex++;
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

    private static List<PhotoSizeAttachmentSubHolder> getPhotos(View sourceView, int... ids) {
        PhotoSizeAttachmentSubHolder.ResourceParams photoSizeResourceParams = new PhotoSizeAttachmentSubHolder.ResourceParams();
        photoSizeResourceParams.bottomBackgroundId = R.id.bottom_background;
        photoSizeResourceParams.bottomImagesIconId = R.id.bottom_images_icon;
        photoSizeResourceParams.bottomOptionalInfoId = R.id.bottom_optional_info;
        photoSizeResourceParams.bottomTitleId = R.id.bottom_title;
        photoSizeResourceParams.photoId = R.id.photo;
        photoSizeResourceParams.topAppIconId = R.id.top_app_icon;
        photoSizeResourceParams.topBackgroundId = R.id.top_background;
        photoSizeResourceParams.topTitleId = R.id.top_title;

        List<PhotoSizeAttachmentSubHolder> result = new ArrayList<>();
        if (ids.length == 1) {
            result.add(new PhotoSizeAttachmentSubHolder(sourceView, photoSizeResourceParams));
        } else {
            for (int id : ids) {
                View photoSizeView = sourceView.findViewById(id);
                result.add(new PhotoSizeAttachmentSubHolder(photoSizeView, photoSizeResourceParams));
            }
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

    private PhotoSizeAttachmentItem createPhotoSizeAttItem(VKApiAttachment apiPhotoSizeAttachment, VKApiPhotoSize photoSize, ViewGroup.LayoutParams layoutParams) {
        switch (apiPhotoSizeAttachment.getType()) {
            case PHOTO:
            case POSTED_PHOTO:
                return new PhotoAttachmentItem(photoSize, layoutParams);
            case VIDEO:
                return new VideoAttachmentItem((VKApiVideo) apiPhotoSizeAttachment, photoSize, layoutParams);
            case APP:
                return new AppAttachmentItem((VKApiApplicationContent) apiPhotoSizeAttachment, photoSize, layoutParams);
            case ALBUM:
                return new AlbumAttachmentItem((VKApiPhotoAlbum) apiPhotoSizeAttachment, photoSize, layoutParams);
            default:
                throw new IllegalArgumentException("There is no photo size attachment item for type " + apiPhotoSizeAttachment.getType());
        }
    }

    private List<LayoutParams> initLayoutParams(List<PhotoSizable> photos) {
        if (photos.size() != 1) {
            return Collections.emptyList();
        } else {
            List<LayoutParams> paramsList = new ArrayList<>();
            VKApiPhotoSize minPhotoSize = PhotoUtil.getMinPhotoSizeForPost(photos.get(0).getPhotoSizes());
            Pair<Integer, Integer> dimensForPhoto = PhotoUtil.getPostPhotoDimensions(minPhotoSize);
            LayoutParams layoutParams = new LayoutParams(dimensForPhoto.first, dimensForPhoto.second);
            setCommonMargin(layoutParams, res().dimenI(R.dimen.margin_medium));
            paramsList.add(layoutParams);
            return paramsList;
        }
    }

    private List<VKApiPhotoSize> initPhotoSizes(List<PhotoSizable> photos) {
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

    private List<VKApiPhotoSize> initPhotoSizesForOne(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        VKApiPhotoSize minPhotoSize = PhotoUtil.getMinPhotoSizeForPost(photos.get(0).getPhotoSizes());
        photoSizeList.add(minPhotoSize);
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForFixed(List<PhotoSizable> photos, float fixed) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        for (PhotoSizable photo : photos) {
            photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photo.getPhotoSizes(), fixed));
        }
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForThree(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 3 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 3));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForFive(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 5 / 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 5 / 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 5 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).getPhotoSizes(), 5 / 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).getPhotoSizes(), 5 / 2));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForSix(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).getPhotoSizes(), 4));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForSeven(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).getPhotoSizes(), 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).getPhotoSizes(), 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).getPhotoSizes(), 5));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).getPhotoSizes(), 5));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForNine(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).getPhotoSizes(), 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).getPhotoSizes(), 3));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(7).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(8).getPhotoSizes(), 4));
        return photoSizeList;
    }

    private List<VKApiPhotoSize> initPhotoSizesForTen(List<PhotoSizable> photos) {
        List<VKApiPhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(0).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(1).getPhotoSizes(), 2));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(2).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(3).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(4).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(5).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(6).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(7).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(8).getPhotoSizes(), 4));
        photoSizeList.add(PhotoUtil.getMinPhotoSizeForSquareInPost(photos.get(9).getPhotoSizes(), 4));
        return photoSizeList;
    }
}
