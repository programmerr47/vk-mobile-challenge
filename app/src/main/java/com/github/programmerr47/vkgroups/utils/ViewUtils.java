package com.github.programmerr47.vkgroups.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author Michael Spitsin
 * @since 2016-01-27
 */
public class ViewUtils {

    public static void setCommonMargin(@NonNull LinearLayout.MarginLayoutParams layoutParams, int margin) {
        layoutParams.topMargin = margin;
        layoutParams.bottomMargin = margin;
        setMarginStartIfPossible(layoutParams, margin);
        setMarginEndIfPossible(layoutParams, margin);
    }

    public static void setMarginEndIfPossible(@NonNull ViewGroup.MarginLayoutParams layoutParams, int margin) {
        if (AndroidUtils.hasJellyBeanMr1()) {
            layoutParams.setMarginEnd(margin);
        } else {
            layoutParams.rightMargin = margin;
        }
    }

    public static void setMarginStartIfPossible(@NonNull LinearLayout.MarginLayoutParams layoutParams, int margin) {
        if (AndroidUtils.hasJellyBeanMr1()) {
            layoutParams.setMarginStart(margin);
        } else {
            layoutParams.leftMargin = margin;
        }
    }

    public static void setVisibilityIfNeed(@NonNull View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }
}
