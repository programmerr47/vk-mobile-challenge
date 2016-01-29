package com.github.programmerr47.vkgroups;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_BOLD;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_REGULAR;

/**
 * @author Michael Spitsin
 * @since 28.01.2016
 */
public class PostDescription {

    private static final String THREE_DOTS = "\u2026";
    private static final String NEW_LINE = "\n";

    private String text;
    private int displayedCollapsedLength = 100;
    private int maxCollapsedLength = 200;
    private boolean isCollapsed = true;

    private String cachedDisplayedDescription;
    private SpannableStringBuilder cachedLink;
    private OnDescriptionRepresentationChangedListener listener;

    public PostDescription(String text, OnDescriptionRepresentationChangedListener listener) {
        this.text = text;
        this.listener = listener;
        this.cachedDisplayedDescription = createDisplayedDescription();
        this.cachedLink = createLink();
    }

    public void appendToTextView(TextView textView) {
        textView.setText(cachedDisplayedDescription);

        if (canCollapseText()) {
            textView.append(cachedLink);
        }
    }

    private String createDisplayedDescription() {
        if (canCollapseText()) {
            if (isCollapsed) {
                return text.substring(0, displayedCollapsedLength) + THREE_DOTS;
            } else {
                return text + NEW_LINE;
            }
        } else {
            return text;
        }
    }

    private SpannableStringBuilder createLink() {
        if (canCollapseText()) {
            if (isCollapsed) {
                return getCollapsedLink();
            } else {
                return getExpandedLink();
            }
        } else {
            return new SpannableStringBuilder("");
        }
    }

    private boolean canCollapseText() {
        return text.length() > maxCollapsedLength;
    }

    private SpannableStringBuilder getCollapsedLink() {
        String targetString = res().string(R.string.read_more).toUpperCase();

        SpannableStringBuilder result = new SpannableStringBuilder(targetString);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.colorAccent)), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_BOLD), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new CollapseClickableSpan(), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return result;
    }

    private SpannableStringBuilder getExpandedLink() {
        String targetString = res().string(R.string.read_less).toUpperCase();

        SpannableStringBuilder result = new SpannableStringBuilder(targetString);

        result.setSpan(new ForegroundColorSpan(res().color(R.color.colorAccent)), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new FontSpan(ROBOTO_BOLD), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new ExpandClickableSpan(), 0, targetString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return result;
    }

    private int getClickPartLengthForCollapsedText() {
        return res().string(R.string.read_more).length();
    }

    private int getClickPartLengthForExpandedText() {
        return res().string(R.string.read_less).length();
    }

    private void collapseText() {
        isCollapsed = true;
        cachedDisplayedDescription = createDisplayedDescription();
        this.cachedLink = createLink();
        listener.onDescriptionChanged(isCollapsed);
    }

    private void expandText() {
        isCollapsed = false;
        cachedDisplayedDescription = createDisplayedDescription();
        this.cachedLink = createLink();
        listener.onDescriptionChanged(isCollapsed);
    }

    private class CollapseClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            collapseText();
        }
    }

    private class ExpandClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            expandText();
        }
    }

    public interface OnDescriptionRepresentationChangedListener {
        void onDescriptionChanged(boolean isCollapsed);
    }
}
