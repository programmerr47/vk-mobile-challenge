package com.github.programmerr47.vkgroups.view;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.Constants;
import com.github.programmerr47.vkgroups.FontSpan;
import com.github.programmerr47.vkgroups.R;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_BOLD;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_MEDIUM;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_REGULAR;

/**
 * @author Michael Spitsin
 * @since 28.01.2016
 */
public class PostDescriptionView extends TextView {

    private static final String THREE_DOTS = "\u2026";
    private static final String NEW_LINE = "\n";

    private String contentText;
    private int displayedCollapsedLength = 100;
    private int maxCollapsedLength = 200;
    private boolean isCollapsed;

    public PostDescriptionView(Context context) {
        super(context);
    }

    private SpannableStringBuilder getDescription() {
        if (canCollapseText()) {
            if (isCollapsed) {
                return getCollapsedDescription();
            } else {
                return getExpandedDescription();
            }
        } else {
            return new SpannableStringBuilder(contentText);
        }
    }

    private boolean canCollapseText() {
        return contentText.length() > maxCollapsedLength;
    }

    private SpannableStringBuilder getCollapsedDescription() {
        String targetString = createCollapsedDescription();
        int clickPartPositionStart = targetString.length() - getClickPartLengthForCollapsedText();

        SpannableStringBuilder result = new SpannableStringBuilder(targetString);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.text_color_primary)), 0, clickPartPositionStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_REGULAR), 0, clickPartPositionStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.colorAccent)), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_BOLD), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new CollapseClickableSpan(), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return result;
    }

    private SpannableStringBuilder getExpandedDescription() {
        String targetString = createExpandedDescription();
        int clickPartPositionStart = targetString.length() - getClickPartLengthForExpandedText();

        SpannableStringBuilder result = new SpannableStringBuilder(targetString);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.text_color_primary)), 0, clickPartPositionStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_REGULAR), 0, clickPartPositionStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.colorAccent)), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_BOLD), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new ExpandClickableSpan(), clickPartPositionStart, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return result;
    }

    private String createCollapsedDescription() {
        return contentText.substring(0, displayedCollapsedLength) +
                THREE_DOTS +
                res().string(R.string.read_more).toUpperCase();

    }

    private String createExpandedDescription() {
        return contentText + NEW_LINE +
                res().string(R.string.read_less).toUpperCase();
    }

    private int getClickPartLengthForCollapsedText() {
        return res().string(R.string.read_more).length();
    }

    private int getClickPartLengthForExpandedText() {
        return res().string(R.string.read_less).length();
    }

    private void collapseText() {
        //TODO
    }

    private void expandText() {
        //TODO
    }

    private class CollapseClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            canCollapseText();
        }
    }

    private class ExpandClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            expandText();
        }
    }
}
