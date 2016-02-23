package com.github.programmerr47.vkgroups.view.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import static com.github.programmerr47.vkgroups.utils.AndroidUtils.toolbarDefaultHeight;

/**
 * @author Michael Spitsin
 * @since 2016-01-15
 */
public class FABDependToolbarScrollBehavior extends FloatingActionButton.Behavior {

    private int toolbarHeight;

    public FABDependToolbarScrollBehavior(Context context, AttributeSet attrs) {
        super();
        toolbarHeight = toolbarDefaultHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return super.layoutDependsOn(parent, fab, dependency) ||
                (dependency instanceof AppBarLayout);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        boolean returnValue = super.onDependentViewChanged(parent, fab, dependency);

        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = fab.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / toolbarHeight;
            fab.setTranslationY(-distanceToScroll * ratio);
        }

        return returnValue;
    }
}
