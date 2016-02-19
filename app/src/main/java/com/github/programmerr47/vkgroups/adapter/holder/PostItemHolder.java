package com.github.programmerr47.vkgroups.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-01-25
 */
public class PostItemHolder extends RecyclerView.ViewHolder {

    private ImageView ownerIconView;
    private TextView ownerTitleView;
    private TextView ownerDateView;
    private TextView postTextView;
    private TextView postExpandCollapseView;

    private TextView likeCountView;
    private TextView repostCountView;
    private TextView commentCountView;

    private List<PostAttachmentSubHolder> attHolders;
    private List<View> photoContainers;
    private List<List<PhotoSizeAttachmentSubHolder>> photoHolders;

    public PostItemHolder(View view, List<PostAttachmentSubHolder> attHolders, List<View> photoContainers, List<List<PhotoSizeAttachmentSubHolder>> photoHolders, ResourceParams params) {
        super(view);

        this.ownerIconView = (ImageView) view.findViewById(params.ownerIconId);
        this.ownerTitleView = (TextView) view.findViewById(params.ownerTitleId);
        this.ownerDateView = (TextView) view.findViewById(params.ownerDateId);
        this.postTextView = (TextView) view.findViewById(params.postTextId);
        this.postExpandCollapseView = (TextView) view.findViewById(params.postExpandCollapseId);
        this.likeCountView = (TextView) view.findViewById(params.likeCountId);
        this.repostCountView = (TextView) view.findViewById(params.repostCountId);
        this.commentCountView = (TextView) view.findViewById(params.commentCountId);
        this.attHolders = attHolders;
        this.photoContainers = photoContainers;
        this.photoHolders = photoHolders;
    }

    public ImageView getOwnerIconView() {
        return ownerIconView;
    }

    public TextView getOwnerTitleView() {
        return ownerTitleView;
    }

    public TextView getOwnerDateView() {
        return ownerDateView;
    }

    public TextView getPostTextView() {
        return postTextView;
    }

    public TextView getPostExpandCollapseView() {
        return postExpandCollapseView;
    }

    public TextView getLikeCountView() {
        return likeCountView;
    }

    public TextView getRepostCountView() {
        return repostCountView;
    }

    public TextView getCommentCountView() {
        return commentCountView;
    }

    public List<PostAttachmentSubHolder> getAttHolders() {
        return attHolders;
    }

    public List<View> getPhotoContainers() {
        return photoContainers;
    }

    public List<List<PhotoSizeAttachmentSubHolder>> getPhotoHolders() {
        return photoHolders;
    }

    public static class ResourceParams {
        public int ownerIconId;
        public int ownerTitleId;
        public int ownerDateId;
        public int postTextId;
        public int postExpandCollapseId;
        public int likeCountId;
        public int repostCountId;
        public int commentCountId;
    }
}
