package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiPlace;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class MapAttachmentItem extends AbstractAttachmentItem {

    private VKApiPlace apiPlace;

    public MapAttachmentItem(VKApiPlace apiPlace) {
        this.apiPlace = apiPlace;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_map_marker_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiPlace.city + ", " + apiPlace.country;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return apiPlace.address;
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
