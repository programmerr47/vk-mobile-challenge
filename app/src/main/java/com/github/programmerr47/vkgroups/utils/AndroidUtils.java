package com.github.programmerr47.vkgroups.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.TypedValue;

import com.github.programmerr47.vkgroups.R;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class AndroidUtils {

    public static ResourceHelper res() {
        return ResourceHelper.INSTANCE;
    }

    public static IdHelper identifiers() {
        return IdHelper.INSTANCE;
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

    public static float pxF(Context context, float dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static float pxF(float dp) {
        return pxF(getAppContext(), dp);
    }

    public static int pxI(Context context, float dp) {
        return (int)pxF(context, dp);
    }

    public static int pxI(float dp) {
        return (int)pxF(getAppContext(), dp);
    }

    public enum ResourceHelper {
        INSTANCE;

        public String string(int id) {
            return string(getAppContext(), id);
        }

        public String string(Context context, int id) {
            return context.getString(id);
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

    public enum IdHelper {
        INSTANCE;

        public int layout(String resName) {
            return layout(getAppContext(), resName);
        }

        public int layout(Context context, String resName) {
            return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
        }

        public int id(String resName) {
            return id(getAppContext(), resName);
        }

        public int id(Context context, String resName) {
            return context.getResources().getIdentifier(resName, "id", context.getPackageName());
        }
    }
}
