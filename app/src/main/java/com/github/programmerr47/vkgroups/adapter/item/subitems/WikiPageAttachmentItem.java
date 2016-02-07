package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiWikiPage;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-02-06
 */
public class WikiPageAttachmentItem extends AbstractAttachmentItem {

    private VKApiWikiPage apiWikiPage;

    public WikiPageAttachmentItem(VKApiWikiPage apiWikiPage) {
        this.apiWikiPage = apiWikiPage;
    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_clipboard_text_white_36dp;
    }

    @NonNull
    @Override
    protected String getTitle() {
        return apiWikiPage.title;
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return res().string(R.string.wiki_page);
    }

    @Nullable
    @Override
    protected String getOptionalInfo() {
        return null;
    }
}
