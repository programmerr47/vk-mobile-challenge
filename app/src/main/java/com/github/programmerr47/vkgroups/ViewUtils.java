package com.github.programmerr47.vkgroups;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.github.programmerr47.vkgroups.AndroidUtils.hasJellyBeanMr1;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;

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
        if (hasJellyBeanMr1()) {
            layoutParams.setMarginEnd(margin);
        } else {
            layoutParams.rightMargin = margin;
        }
    }

    public static void setMarginStartIfPossible(@NonNull LinearLayout.MarginLayoutParams layoutParams, int margin) {
        if (hasJellyBeanMr1()) {
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
