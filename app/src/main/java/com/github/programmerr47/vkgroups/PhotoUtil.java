package com.github.programmerr47.vkgroups;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Pair;

import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKPhotoSizes;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 01.02.2016
 */
public class PhotoUtil {

    private static final float MAX_RATIO = 5.0f;
    private static final float MIN_RATIO = 0.2f;

    public static Pair<Integer, Integer> getMaxDimensionsOfPostPhoto() {
        return getDimensionsOfPostPhoto(MAX_RATIO);
    }

    public static Pair<Integer, Integer> getMinDimensionsOfPostPhoto() {
        return getDimensionsOfPostPhoto(MIN_RATIO);
    }

    private static Pair<Integer, Integer> getDimensionsOfPostPhoto(float ratio) {
        float marginMedium = res().dimen(R.dimen.margin_medium);
        Resources applicationResources = VKGroupApplication.getAppContext().getResources();
        DisplayMetrics displayMetrics = applicationResources.getDisplayMetrics();

        int resultWidth = displayMetrics.widthPixels - (int)(marginMedium * 4);
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
        return getPostPhotoDimensions(new Pair<>(photoSize.width, photoSize.height));
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
}
