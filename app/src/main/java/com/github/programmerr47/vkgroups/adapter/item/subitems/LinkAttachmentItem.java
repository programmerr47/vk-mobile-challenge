package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiLink;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class LinkAttachmentItem extends AbstractAttachmentItem {

    private VKApiLink apiLink;

    public LinkAttachmentItem(VKApiLink apiLink) {
        this.apiLink = apiLink;
    }

    @Override
    protected String getIconUrl() {
        return apiLink.image_src;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_link_variant_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiLink.title;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        if (!TextUtils.isEmpty(apiLink.caption)) {
            return apiLink.caption;
        } else if (!TextUtils.isEmpty(apiLink.description)) {
            return apiLink.description;
        } else {
            return apiLink.url;
        }
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
