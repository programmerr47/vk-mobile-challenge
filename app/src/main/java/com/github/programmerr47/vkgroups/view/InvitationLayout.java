package com.github.programmerr47.vkgroups.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.github.programmerr47.vkgroups.R;

/**
 * @author Mihail Spitsin
 * @since 2/29/2016
 */
public class InvitationLayout extends FrameLayout {

    private static Drawable layerShadowDrawable;
    private static Drawable menuShadowDrawable;

    public InvitationLayout(Context context) {
        super(context);
        init();
    }

    public InvitationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InvitationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InvitationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean result = super.drawChild(canvas, child, drawingTime);

        if (child.getId() == R.id.community_info) {
            layerShadowDrawable.setBounds((int) child.getX() - layerShadowDrawable.getIntrinsicWidth(), child.getTop(), (int) child.getX(), child.getBottom());
            layerShadowDrawable.draw(canvas);

            menuShadowDrawable.setBounds(child.getRight(), child.getTop(), child.getRight() + menuShadowDrawable.getIntrinsicWidth(), child.getBottom());
            menuShadowDrawable.draw(canvas);
        }

        return result;
    }

    private void init() {
        if (layerShadowDrawable == null) {
            layerShadowDrawable = getResources().getDrawable(R.drawable.layer_shadow);
        }

        if (menuShadowDrawable == null) {
            menuShadowDrawable = getResources().getDrawable(R.drawable.menu_shadow);
        }
    }
}
