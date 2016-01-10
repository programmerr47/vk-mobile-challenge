package com.github.programmerr47.vkgroups.pager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author Michael Spitsin
 * @since 2016-01-09
 */
public class VkPagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_ALPHA_SLIDE = 0.35f;

    @Override
    public void transformPage(View page, float position) {
        final float alpha;
        final float scale;
        final float translationX;

        if (position < 0 && position > -1) {
            // this is the page to the left
            scale = 1;
            alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position));
            int pageWidth = page.getWidth();
            float translateValue = position * -pageWidth;
            if (translateValue > -pageWidth) {
                translationX = translateValue;
            } else {
                translationX = 0;
            }
        } else {
            alpha = 1;
            scale = 1;
            translationX = 0;
        }

        page.setAlpha(alpha);
        page.setTranslationX(translationX);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }
}
