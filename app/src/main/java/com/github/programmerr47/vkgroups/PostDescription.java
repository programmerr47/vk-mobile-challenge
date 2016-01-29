package com.github.programmerr47.vkgroups;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_BOLD;

/**
 * @author Michael Spitsin
 * @since 28.01.2016
 */
public class PostDescription {

    private static final String THREE_DOTS = "\u2026";

    private static final int DISPLAYED_COLLAPSED_LENGTH = 100;
    private static final int MAX_COLLAPSED_LENGTH = 200;

    private String text;
    private boolean isCollapsed = true;

    private String cachedDisplayedDescription;
    private OnDescriptionRepresentationChangedListener listener;

    public PostDescription(String text, OnDescriptionRepresentationChangedListener listener) {
        this.text = text;
        this.listener = listener;
        this.cachedDisplayedDescription = createDisplayedDescription();
    }

    public void appendToDescriptionTextView(TextView textView) {
        if (!text.isEmpty()) {
            textView.setVisibility(VISIBLE);
            textView.setText(cachedDisplayedDescription);
        } else {
            textView.setVisibility(GONE);
        }
    }

    public void appendToExpandCollapseTextView(TextView textView) {
        if (canCollapseText()) {
            textView.setVisibility(VISIBLE);

            if (isCollapsed) {
                textView.setText(R.string.read_more);
            } else {
                textView.setText(R.string.read_less);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCollapsed) {
                        expandText();
                    } else {
                        collapseText();
                    }
                }
            });
        } else {
            textView.setVisibility(GONE);
        }
    }

    public boolean canCollapseText() {
        return text.length() > MAX_COLLAPSED_LENGTH &&
                textCollapseEnd(MAX_COLLAPSED_LENGTH) < text.length() - 1;
    }

    private String createDisplayedDescription() {
        if (canCollapseText()) {
            if (isCollapsed) {
                return text.substring(0, textCollapseEnd(DISPLAYED_COLLAPSED_LENGTH)) + THREE_DOTS;
            } else {
                return text;
            }
        } else {
            return text;
        }
    }

    private int textCollapseEnd(int startIndex) {
        for (int i = startIndex; i < text.length(); i++) {
            char curChar = text.charAt(i);
            if (!Character.isLetter(curChar) && !Character.isDigit(curChar)) {
                return i;
            }
        }

        return text.length() - 1;
    }

    private void collapseText() {
        isCollapsed = true;
        cachedDisplayedDescription = createDisplayedDescription();
        listener.onDescriptionChanged(isCollapsed);
    }

    private void expandText() {
        isCollapsed = false;
        cachedDisplayedDescription = createDisplayedDescription();
        listener.onDescriptionChanged(isCollapsed);
    }

    public interface OnDescriptionRepresentationChangedListener {
        void onDescriptionChanged(boolean isCollapsed);
    }
}
