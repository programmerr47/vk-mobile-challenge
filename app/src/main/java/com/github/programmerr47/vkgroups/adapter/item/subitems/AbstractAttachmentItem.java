package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.adapter.holder.PostAttachmentSubHolder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Michael Spitsin
 * @since 05.02.2016
 */
public abstract class AbstractAttachmentItem implements AttachmentItem {

    @Override
    public void bindView(PostAttachmentSubHolder holder) {
        holder.getIconView().setImageResource(getIconId());
        holder.getTitleView().setText(getTitle());
        setTextOrGone(holder.getSubtitleView(), getSubtitle());
        setTextOrGone(holder.getOptionalInfoView(), getOptionalInfo());
    }

    protected abstract int getIconId();

    @NonNull
    protected abstract String getTitle();

    @Nullable
    protected abstract String getSubtitle();

    @Nullable
    protected abstract String getOptionalInfo();

    private void setTextOrGone(TextView textView, @Nullable String text) {
        if (text == null || text.isEmpty()) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(text);
        }
    }
}
