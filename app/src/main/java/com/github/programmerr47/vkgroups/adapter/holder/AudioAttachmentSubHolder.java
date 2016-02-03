package com.github.programmerr47.vkgroups.adapter.holder;

import android.view.View;
import android.widget.TextView;

/**
 * @author Michael Spitsin
 * @since 03.02.2016
 */
public class AudioAttachmentSubHolder {

    private TextView artistView;
    private TextView titleView;
    private TextView durationView;

    public AudioAttachmentSubHolder(View view, ResourceParams params) {
        this.artistView = (TextView) view.findViewById(params.artistViewId);
        this.titleView = (TextView) view.findViewById(params.titleViewId);
        this.durationView = (TextView) view.findViewById(params.durationViewId);
    }

    public TextView getArtistView() {
        return artistView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDurationView() {
        return durationView;
    }

    public static class ResourceParams {
        public int artistViewId;
        public int titleViewId;
        public int durationViewId;
    }
}
