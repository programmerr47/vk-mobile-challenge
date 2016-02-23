package com.github.programmerr47.vkgroups.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import static com.github.programmerr47.vkgroups.utils.AndroidUtils.getAssetsTypeface;

/**
 * @author Michael Spitsin
 * @since 28.01.2016
 */
public class FontSpan extends TypefaceSpan {

    private Typeface mNewTypeface;

    public FontSpan(Constants.Font font) {
        super("");
        this.mNewTypeface = AndroidUtils.getAssetsTypeface(font);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        applyCustomTypeFace(ds, mNewTypeface);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, mNewTypeface);
    }

    private void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
