package com.github.programmerr47.vkgroups.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.view.PostContentView;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-01-25
 */
public class PostItemHolder extends RecyclerView.ViewHolder {

    private ImageView likeIconView;
    private ImageView repostIconView;
    private ImageView commentIconView;
    private TextView likeCountView;
    private TextView repostCountView;
    private TextView commentCountView;
    private PostContentView ownerContentView;
    private List<PostContentView> repostContentViews;

    public PostItemHolder(View view, PostContentView ownerContentView, ResourceParams params) {
        super(view);
        this.ownerContentView = ownerContentView;
        this.likeIconView = (ImageView) view.findViewById(params.likeIconId);
        this.repostIconView = (ImageView) view.findViewById(params.repostIconId);
        this.commentIconView = (ImageView) view.findViewById(params.commentIconId);
        this.likeCountView = (TextView) view.findViewById(params.likeCountId);
        this.repostCountView = (TextView) view.findViewById(params.repostCountId);
        this.commentCountView = (TextView) view.findViewById(params.commentCountId);
    }

    public ImageView getLikeIconView() {
        return likeIconView;
    }

    public ImageView getRepostIconView() {
        return repostIconView;
    }

    public ImageView getCommentIconView() {
        return commentIconView;
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

    public PostContentView getOwnerContentView() {
        return ownerContentView;
    }

    public List<PostContentView> getRepostContentViews() {
        return repostContentViews;
    }

    public static class ResourceParams {
        public int likeIconId;
        public int repostIconId;
        public int commentIconId;
        public int likeCountId;
        public int repostCountId;
        public int commentCountId;
    }
}
