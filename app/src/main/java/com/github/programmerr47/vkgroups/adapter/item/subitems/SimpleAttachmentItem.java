package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKAttachments;

/**
 * @author Michael Spitsin
 * @since 05.02.2016
 */
public class SimpleAttachmentItem extends AbstractPostAttachmentItem {

    VKAttachments.VKApiAttachment apiAttachment;

    public SimpleAttachmentItem(VKAttachments.VKApiAttachment apiAttachment) {
        this.apiAttachment = apiAttachment;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_plus_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return "Add here attachment: " + apiAttachment.getType();
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return apiAttachment.getClass().getName();
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
