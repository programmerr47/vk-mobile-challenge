package com.github.programmerr47.vkgroups;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class AndroidUtils {

    public static Typeface getAssetsTypeface(Context context, Constants.Font font) {
        return Typeface.createFromAsset(context.getAssets(), Constants.ASSETS_FONTS_DIR + font.getFontName());
    }
}
