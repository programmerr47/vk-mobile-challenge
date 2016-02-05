package com.github.programmerr47.vkgroups.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Michael Spitsin
 * @since 05.02.2016
 */
public class PostAttachmentSubHolder {

    private final View holderView;
    private final ImageView iconView;
    private final TextView titleView;
    private final TextView subtitleView;
    private final TextView optionalInfoView;

    public PostAttachmentSubHolder(View attachmentView, ResourceParams resourceParams) {
        holderView = attachmentView;
        iconView = (ImageView) attachmentView.findViewById(resourceParams.iconId);
        titleView = (TextView) attachmentView.findViewById(resourceParams.titleId);
        subtitleView = (TextView) attachmentView.findViewById(resourceParams.subtitleId);
        optionalInfoView = (TextView) attachmentView.findViewById(resourceParams.optionalInfoId);
    }

    public View getHolderView() {
        return holderView;
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getSubtitleView() {
        return subtitleView;
    }

    public TextView getOptionalInfoView() {
        return optionalInfoView;
    }

    public static class ResourceParams {
        public int iconId;
        public int titleId;
        public int subtitleId;
        public int optionalInfoId;
    }
}
