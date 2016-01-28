package com.github.programmerr47.vkgroups;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class AndroidUtils {

    public static ResourceHelper res() {
        return ResourceHelper.INSTANCE;
    }

    public static Typeface getAssetsTypeface(Constants.Font font) {
        return getAssetsTypeface(getAppContext(), font);
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

    public static boolean hasJellyBeanMr1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public enum ResourceHelper {
        INSTANCE;

        public String string(int id) {
            return string(getAppContext(), id);
        }

        public String string(Context context, int id) {
            return context.getResources().getString(id);
        }

        public String plural(int id, int quantity, Object... formatArgs) {
            return plural(getAppContext(), id, quantity, formatArgs);
        }

        public String plural(Context context, int id, int quantity, Object... formatArgs) {
            return context.getResources().getQuantityString(id, quantity, formatArgs);
        }

        public int dimenI(int id) {
            return (int) dimen(id);
        }

        public float dimen(int id) {
            return dimen(getAppContext(), id);
        }

        public float dimen(Context context, int id) {
            return context.getResources().getDimension(id);
        }

        @ColorInt
        public int color(@ColorRes int id) {
            return color(getAppContext(), id);
        }

        @ColorInt
        public int color(Context context, @ColorRes int id) {
            return context.getResources().getColor(id);
        }
    }
}
