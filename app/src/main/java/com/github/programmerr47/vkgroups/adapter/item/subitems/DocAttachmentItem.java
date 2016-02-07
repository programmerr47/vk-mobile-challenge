package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiDocument;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class DocAttachmentItem extends AbstractAttachmentItem {

    private VKApiDocument apiDocument;

    public DocAttachmentItem(VKApiDocument apiDocument) {
        this.apiDocument = apiDocument;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_file_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiDocument.title + "." + apiDocument.ext;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        if (apiDocument.isGif()) {
            return res().string(R.string.gif_image);
        } else if (apiDocument.isImage()) {
            return res().string(R.string.image);
        } else {
            return res().string(R.string.file);
        }
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return String.valueOf(apiDocument.size);
    }
}
