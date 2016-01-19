package com.github.programmerr47.vkgroups;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class AndroidUtils {

    public static ResourceHelper res() {
        return ResourceHelper.INSTANCE;
    }

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

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public enum ResourceHelper {
        INSTANCE;

        public String plural(int id, int quantity, Object... formatArgs) {
            return plural(getAppContext(), id, quantity, formatArgs);
        }

        public String plural(Context context, int id, int quantity, Object... formatArgs) {
            return context.getResources().getQuantityString(id, quantity, formatArgs);
        }
    }
}
