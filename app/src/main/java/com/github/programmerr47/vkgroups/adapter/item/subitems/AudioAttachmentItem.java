package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.DurationFormatter;
import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiAudio;

import static com.github.programmerr47.vkgroups.DurationFormatter.formatDuration;

/**
 * @author Michael Spitsin
 * @since 05.02.2016
 */
public class AudioAttachmentItem extends AbstractAttachmentItem {

    private VKApiAudio apiAudio;

    public AudioAttachmentItem(VKApiAudio apiAudio) {
        this.apiAudio = apiAudio;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_music_note_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiAudio.artist;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return apiAudio.title;
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return formatDuration(apiAudio.duration);
    }
}
