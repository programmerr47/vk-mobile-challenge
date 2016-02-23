package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiNote;

import static com.github.programmerr47.vkgroups.utils.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class NoteAttachmentItem extends AbstractAttachmentItem {

    private VKApiNote apiNote;

    public NoteAttachmentItem(VKApiNote apiNote) {
        this.apiNote = apiNote;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_note_text_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiNote.title;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return res().string(R.string.note);
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
