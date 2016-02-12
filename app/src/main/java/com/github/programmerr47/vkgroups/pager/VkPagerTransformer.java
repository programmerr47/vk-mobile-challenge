package com.github.programmerr47.vkgroups.pager;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.programmerr47.vkgroups.pager.pages.Page;

/**
 * @author Michael Spitsin
 * @since 2016-01-09
 */
public class VkPagerTransformer implements ViewPager.PageTransformer {

    private static final float MAX_ALPHA_SLIDE = 0.7f;

    @Override
    public void transformPage(View page, float position) {
        final float translationX;
        View pageVeilView = page.findViewById(Page.VEIL_ID);

        if (position < 0 && position > -1) {
            // this is the page to the left
            float alpha = Math.min(MAX_ALPHA_SLIDE, Math.abs(position));
            pageVeilView.setAlpha(alpha);

            int pageWidth = page.getWidth();
            float translateValue = position * -pageWidth;
            if (translateValue > -pageWidth) {
                translationX = translateValue;
            } else {
                translationX = 0;
            }
        } else {
            pageVeilView.setAlpha(0);
            translationX = 0;
        }

        page.setTranslationX(translationX);
    }
}
