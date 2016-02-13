package com.github.programmerr47.vkgroups;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static com.github.programmerr47.vkgroups.AndroidUtils.hasJellyBeanMr1;

/**
 * @author Michael Spitsin
 * @since 2016-01-27
 */
public class ViewUtils {

    public static void setCommonMargin(LinearLayout.LayoutParams layoutParams, int margin) {
        layoutParams.topMargin = margin;
        layoutParams.bottomMargin = margin;
        setMarginStartIfPossible(layoutParams, margin);
        setMarginEndIfPossible(layoutParams, margin);
    }

    public static void setMarginEndIfPossible(ViewGroup.MarginLayoutParams layoutParams, int margin) {
        if (hasJellyBeanMr1()) {
            layoutParams.setMarginEnd(margin);
        } else {
            layoutParams.rightMargin = margin;
        }
    }

    public static void setMarginStartIfPossible(LinearLayout.LayoutParams layoutParams, int margin) {
        if (hasJellyBeanMr1()) {
            layoutParams.setMarginStart(margin);
        } else {
            layoutParams.leftMargin = margin;
        }
    }
}
