package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.PostAttachmentSubHolder;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.github.programmerr47.vkgroups.utils.AndroidUtils.pxI;
import static com.github.programmerr47.vkgroups.utils.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 05.02.2016
 */
public abstract class AbstractAttachmentItem implements AttachmentItem {

    private static final LinearLayout.LayoutParams BIG_PARAMS = new LinearLayout.LayoutParams(MATCH_PARENT, res().dimenI(R.dimen.list_item_height_default));
    private static final LinearLayout.LayoutParams SMALL_PARAMS = new LinearLayout.LayoutParams(MATCH_PARENT, res().dimenI(R.dimen.list_item_height_small));

    private static final Drawable DEF_BACKGROUND = getAppContext().getResources().getDrawable(R.drawable.bg_attachment_default);

    @Override
    public void bindView(PostAttachmentSubHolder holder) {
        if (!TextUtils.isEmpty(getIconUrl())) {
            holder.getHolderView().setLayoutParams(BIG_PARAMS);

            holder.getIconView().setBackgroundDrawable(null);
            holder.getIconView().setPadding(0, 0, 0, 0);
            Picasso.with(getAppContext()).load(getIconUrl()).into(holder.getIconView());
        } else {
            holder.getHolderView().setLayoutParams(SMALL_PARAMS);

            holder.getIconView().setBackgroundDrawable(DEF_BACKGROUND);
            holder.getIconView().setPadding(res().dimenI(R.dimen.margin_medium), res().dimenI(R.dimen.margin_medium), res().dimenI(R.dimen.margin_medium), res().dimenI(R.dimen.margin_medium));
            holder.getIconView().setImageResource(getIconId());
        }

        holder.getTitleView().setText(getTitle());

        setTextOrGone(holder.getSubtitleView(), getSubtitle());

        holder.getOptionalInfoView().setGravity(getOptionalInfoViewGravity());
        setTextOrGone(holder.getOptionalInfoView(), getOptionalInfo());
    }

    protected String getIconUrl() {
        return null;
    }

    protected int getOptionalInfoViewGravity() {
        return Gravity.BOTTOM;
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
