package com.github.programmerr47.vkgroups.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Pair;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.VKGroupApplication;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKPhotoSizes;

/**
 * @author Michael Spitsin
 * @since 01.02.2016
 */
public class PhotoUtil {

    private static final float MAX_RATIO = 5.0f;
    private static final float MIN_RATIO = 0.2f;

    public static Pair<Integer, Integer> getDimensionsOfSquarePostPhoto(float widthRatio) {
        return getDimensionsOfPostPhoto(1.0f, widthRatio);
    }

    public static Pair<Integer, Integer> getMaxDimensionsOfPostPhoto() {
        return getDimensionsOfPostPhoto(MAX_RATIO);
    }

    public static Pair<Integer, Integer> getMinDimensionsOfPostPhoto() {
        return getDimensionsOfPostPhoto(MIN_RATIO);
    }

    private static Pair<Integer, Integer> getDimensionsOfPostPhoto(float ratio) {
        return getDimensionsOfPostPhoto(ratio, 1);
    }

    private static Pair<Integer, Integer> getDimensionsOfPostPhoto(float ratio, float widthRatio) {
        float marginMedium = AndroidUtils.res().dimen(R.dimen.margin_medium);
        float marginSmall = AndroidUtils.res().dimen(R.dimen.margin_small);
        Resources applicationResources = VKGroupApplication.getAppContext().getResources();
        DisplayMetrics displayMetrics = applicationResources.getDisplayMetrics();

        int resultWidth = (displayMetrics.widthPixels - (int)(marginMedium * 2) - (int)(marginSmall * 2));
        resultWidth = (int)(resultWidth / widthRatio - marginSmall * 2);
        int resultHeight = (int)(resultWidth * ratio);

        return new Pair<>(resultWidth, resultHeight);
    }

    public static Pair<Integer, Integer> getPostPhotoDimensions(Pair<Integer, Integer> realDimensions, Pair<Integer, Integer> maxDimensions, Pair<Integer, Integer> minDimensions) {
        int scaledHeight = (int)(maxDimensions.first * realDimensions.second * 1.0 / realDimensions.first);
        int scaledWidth = maxDimensions.first;

        if (scaledHeight > maxDimensions.second) {
            scaledHeight = maxDimensions.second;
//            scaledWidth = (int)(maxDimensions.second * realDimensions.first * 1.0 / realDimensions.second);
        } else if (scaledHeight < minDimensions.second) {
            scaledHeight = minDimensions.second;
        }

        return new Pair<>(scaledWidth, scaledHeight);
    }

    public static Pair<Integer, Integer> getPostPhotoDimensions(Pair<Integer, Integer> realDimensions) {
        return getPostPhotoDimensions(realDimensions, getMaxDimensionsOfPostPhoto(), getMinDimensionsOfPostPhoto());
    }

    public static Pair<Integer, Integer> getPostPhotoDimensions(VKApiPhotoSize photoSize) {
        if (photoSize == null) {
            return getDimensionsOfPostPhoto(0.5f);
        } else {
            return getPostPhotoDimensions(new Pair<>(photoSize.width, photoSize.height));
        }
    }

    public static VKApiPhotoSize getMinPhotoSize(VKPhotoSizes photoSizes, Pair<Integer, Integer> minDimensions) {
        for (VKApiPhotoSize photoSize : photoSizes) {
            if (photoSize.width >= minDimensions.first || photoSize.height >= minDimensions.second) {
                return photoSize;
            }
        }

        if (!photoSizes.isEmpty()) {
            return photoSizes.get(photoSizes.size() - 1);
        } else {
            return null;
        }
    }

    public static VKApiPhotoSize getMinPhotoSizeForPost(VKPhotoSizes photoSizes) {
        return getMinPhotoSize(photoSizes, getMaxDimensionsOfPostPhoto());
    }

    public static VKApiPhotoSize getMinPhotoSizeForSquareInPost(VKPhotoSizes photoSizes, float widthRatio) {
        return getMinPhotoSize(photoSizes, getDimensionsOfSquarePostPhoto(widthRatio));
    }
}
