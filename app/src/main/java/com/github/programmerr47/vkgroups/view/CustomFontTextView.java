package com.github.programmerr47.vkgroups.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.Constants;
import com.github.programmerr47.vkgroups.R;

import static com.github.programmerr47.vkgroups.AndroidUtils.getAssetsTypeface;

/**
 * Extension of standard {@link TextView} that can specify type of font.
 * All not-standard fonts stored at assets folder, in {@link Constants#ASSETS_FONTS_DIR} directory.
 *
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            String fontName = typedArray.getString(R.styleable.CustomFontTextView_fontName);

            if (fontName != null) {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.ASSETS_FONTS_DIR + fontName);
                setTypeface(typeface);
            }
            typedArray.recycle();
        }
    }

    @SuppressWarnings("unused")
    public void setFont(Constants.Font font) {
        if (font != null && font.getFontName() != null) {
            Typeface typeface = getAssetsTypeface(getContext(), font);
            setTypeface(typeface);
        }
    }
}
