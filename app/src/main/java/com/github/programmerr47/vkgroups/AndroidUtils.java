package com.github.programmerr47.vkgroups;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class AndroidUtils {

    public static Typeface getAssetsTypeface(Context context, Constants.Font font) {
        return Typeface.createFromAsset(context.getAssets(), Constants.ASSETS_FONTS_DIR + font.getFontName());
    }

    public static int toolbarDefaultHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
