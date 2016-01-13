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

    private ImageView avatarView;
    private TextView titleView;
    private TextView typeView;

    public CommunityItemHolder(View itemView,ResourceParams params) {
        super(itemView);

        avatarView = (ImageView) itemView.findViewById(params.avatarId);
        titleView = (TextView) itemView.findViewById(params.titleId);
        typeView = (TextView) itemView.findViewById(params.typeId);
    }

    public ImageView getAvatarView() {
        return avatarView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getTypeView() {
        return typeView;
    }

    public static class ResourceParams {
        public int avatarId;
        public int titleId;
        public int typeId;
    }
}
