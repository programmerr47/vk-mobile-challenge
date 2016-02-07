package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.AndroidUtils;
import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiPost;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class PostAttachmentItem extends AbstractAttachmentItem {

    private VKApiPost apiPost;

    public PostAttachmentItem(VKApiPost apiPost) {
        this.apiPost = apiPost;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_tooltip_edit_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return res().string(R.string.post);
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return String.valueOf(apiPost.date);
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
