package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiPoll;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-02-07
 */
public class PollAttachmentItem extends AbstractAttachmentItem {

    private VKApiPoll apiPoll;

    public PollAttachmentItem(VKApiPoll apiPoll) {
        this.apiPoll = apiPoll;
    }


    @Override
    protected int getIconId() {
        return R.drawable.ic_poll_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiPoll.question;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return res().plural(R.plurals.poll_votes, apiPoll.votes, apiPoll.votes);
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
