package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiAudio;

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

    private String formatDuration(int durSec) {
        int durMin = durSec / 60;
        int remSec = durSec - durMin * 60;
        String result = formatTwoDigitNumber(remSec);

        if (durMin > 0) {
            int durH = durMin / 60;
            int remMin = durMin - durH * 60;

            if (durH > 0) {
                return durH + ":" + formatTwoDigitNumber(remMin) + ":" + result;
            } else {
                return remMin + ":" + result;
            }
        } else {
            return "0:" + result;
        }

    }

    private String formatTwoDigitNumber(int n) {
        return n > 10 ? String.valueOf(n) : "0" + n;
    }
}
