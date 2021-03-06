package com.github.programmerr47.vkgroups.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Michael Spitsin
 * @since 2016-01-13
 */
public class CommunityItemHolder extends RecyclerView.ViewHolder {

    private final ImageView avatarView;
    private final TextView titleView;
    private final TextView subInfoView;

    public CommunityItemHolder(View itemView,ResourceParams params) {
        super(itemView);

        avatarView = (ImageView) itemView.findViewById(params.avatarId);
        titleView = (TextView) itemView.findViewById(params.titleId);
        subInfoView = (TextView) itemView.findViewById(params.subInfoId);
    }

    public ImageView getAvatarView() {
        return avatarView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getSubInfoView() {
        return subInfoView;
    }

    public static class ResourceParams {
        public int avatarId;
        public int titleId;
        public int subInfoId;
    }
}
