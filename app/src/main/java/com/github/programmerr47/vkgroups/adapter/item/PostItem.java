package com.github.programmerr47.vkgroups.adapter.item;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.github.programmerr47.vkgroups.DateFormatter;
import com.github.programmerr47.vkgroups.PhotoUtil;
import com.github.programmerr47.vkgroups.PostDescription;
import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.AudioAttachmentSubHolder;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.WikiPageSubHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.PostItemHolderProducer;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.github.programmerr47.vkgroups.view.PostContentView;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiWikiPage;
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
import static com.vk.sdk.api.model.VKAttachments.TYPE_PHOTO;
import static com.vk.sdk.api.model.VKAttachments.TYPE_POSTED_PHOTO;

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
    private List<VKApiWikiPage> attWikiPages;

    private List<LayoutParams> photosParams;
    private List<VKApiPhotoSize> photoSizes;

    private List<PostDescription> historyDescriptions;

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
            VKApiPhotoSize photoSize = photoSizes.get(i);
            ImageView photoView = ownerContentView.getPhotos().get(i);

            if (i < photosParams.size()) {
                photoView.setLayoutParams(photosParams.get(i));
            }

            Picasso.with(getAppContext()).load(photoSize.src).into(photoView);
        }

        for (int i = 0; i < attAudios.size(); i++) {
            VKApiAudio audio = attAudios.get(i);
            AudioAttachmentSubHolder audioHolder = viewHolder.getAudioAttachmentViews().get(i);

            audioHolder.getArtistView().setText(audio.artist);
            audioHolder.getTitleView().setText(audio.title);
            audioHolder.getDurationView().setText(String.valueOf(audio.duration));
        }

        for (int i = 0; i < attWikiPages.size(); i++) {
            VKApiWikiPage wikiPage = attWikiPages.get(i);
            WikiPageSubHolder wikiPageHolder = viewHolder.getWikiPageAttachmentViews().get(i);

            wikiPageHolder.getNameView().setText(wikiPage.title);
            wikiPageHolder.getTypeView().setText(res().string(R.string.wiki_page));
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

                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                PostContentView ownerPostContent = new PostContentView(postSections.getContext());
                ownerPostContent.setLayoutParams(layoutParams);

                List<ImageView> photos = new ArrayList<>();
                View view;
                switch (attPhotos.size()) {
                    case 2:
                        view = layoutInflater.inflate(R.layout.attachment_photo_2, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2);
                        break;
                    case 3:
                        view = layoutInflater.inflate(R.layout.attachment_photo_3, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3);
                        break;
                    case 4:
                        view = layoutInflater.inflate(R.layout.attachment_photo_4, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4);
                        break;
                    case 5:
                        view = layoutInflater.inflate(R.layout.attachment_photo_5, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5);
                        break;
                    case 6:
                        view = layoutInflater.inflate(R.layout.attachment_photo_6, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5, R.id.photo_6);
                        break;
                    case 7:
                        view = layoutInflater.inflate(R.layout.attachment_photo_7, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                                R.id.photo_7);
                        break;
                    case 8:
                        view = layoutInflater.inflate(R.layout.attachment_photo_8, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                                R.id.photo_7, R.id.photo_8);
                        break;
                    case 9:
                        view = layoutInflater.inflate(R.layout.attachment_photo_9, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                                R.id.photo_7, R.id.photo_8, R.id.photo_9);
                        break;
                    case 10:
                        view = layoutInflater.inflate(R.layout.attachment_photo_10, ownerPostContent, true);
                        getPhotos(view, photos,
                                R.id.photo_1, R.id.photo_2, R.id.photo_3,
                                R.id.photo_4, R.id.photo_5, R.id.photo_6,
                                R.id.photo_7, R.id.photo_8, R.id.photo_9,
                                R.id.photo_10);
                        break;
                    default:
                        for (VKApiPhoto attPhoto : attPhotos) {
                            view = layoutInflater.inflate(R.layout.attachment_photo, ownerPostContent, false);
                            ImageView photo = (ImageView) view.findViewById(R.id.photo_1);
                            ownerPostContent.addView(view);
                            photos.add(photo);
                        }
                }

                ownerPostContent.setPhotos(photos);

                List<AudioAttachmentSubHolder> audioHolders = new ArrayList<>();
                View audioView;
                for (VKApiAudio attAudio : attAudios) {
                    audioView = layoutInflater.inflate(R.layout.attachment_audio, ownerPostContent, false);

                    AudioAttachmentSubHolder.ResourceParams params = new AudioAttachmentSubHolder.ResourceParams();
                    params.artistViewId = R.id.artist;
                    params.titleViewId = R.id.title;
                    params.durationViewId = R.id.duration;

                    ownerPostContent.addView(audioView);
                    audioHolders.add(new AudioAttachmentSubHolder(audioView, params));
                }

                List<WikiPageSubHolder> wikiPageHolders = new ArrayList<>();
                View wikiPageView;
                for (VKApiWikiPage attWikiPage : attWikiPages) {
                    wikiPageView = layoutInflater.inflate(R.layout.attachment_wiki_page, ownerPostContent, false);

                    WikiPageSubHolder.ResourceParams params = new WikiPageSubHolder.ResourceParams();
                    params.nameViewId = R.id.name;
                    params.typeViewId = R.id.type;

                    ownerPostContent.addView(wikiPageView);
                    wikiPageHolders.add(new WikiPageSubHolder(wikiPageView, params));
                }

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

                return new PostItemHolder(baseView, ownerPostContent, audioHolders, wikiPageHolders, params);
            }
        };
    }

    public String getItemId() {
        return "p:" + attPhotos.size() + ";a:" + attAudios.size() + ";w:" + attWikiPages.size();
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
        attWikiPages = getAttachments(post, VKApiWikiPage.class);
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
}
