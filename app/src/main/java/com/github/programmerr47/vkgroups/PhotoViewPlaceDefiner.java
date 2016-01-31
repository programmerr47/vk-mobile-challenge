package com.github.programmerr47.vkgroups;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import static com.github.programmerr47.vkgroups.AndroidUtils.pxF;

/**
 * @author Michael Spitsin
 * @since 2016-01-30
 */
public class PhotoViewPlaceDefiner {

    private static final float SPACE_SIZE = pxF(4);
    private static final float MIN_CONTROL_RATIO = 0.2f;
    private static final float MAX_CONTROL_RATIO = 2;

    private List<RectF> originalRects;

    private int resultHeight;

    public PhotoViewPlaceDefiner(List<RectF> originalRects) {
        this.originalRects = originalRects;
    }

    public int getResultHeight() {
        return resultHeight;
    }

    public List<RectF> definePlaces(RectF mainArea) {
        switch (originalRects.size()) {
            case 1:
                return definePlacesForOne(mainArea);
            case 2:
                return definePlacesForTwo(mainArea);
            case 3:
                return definePlacesForThree(mainArea);
            case 4:
                return definePlacesForFour(mainArea);
            case 5:
                return definePlacesForFive(mainArea);
            case 6:
                return definePlacesForSix(mainArea);
            case 7:
                return definePlacesForSeven(mainArea);
            case 8:
                return definePlacesForEight(mainArea);
            case 9:
                return definePlacesForNine(mainArea);
            case 10:
                return definePlacesForTen(mainArea);
            default:
                return definePlacesForOne(mainArea);
        }
    }

    private List<RectF> definePlacesForOne(RectF mainArea) {
        RectF originalRect = originalRects.get(0);
        float originalRatio = originalRect.height() / originalRect.width();

        RectF resultRect;
        if (originalRatio >= MIN_CONTROL_RATIO && originalRatio <= MAX_CONTROL_RATIO) {
            float scale = mainArea.width() / originalRect.width();
            float resultBottom = mainArea.top + originalRect.height() * scale;
            resultRect = new RectF(mainArea.left, mainArea.top, mainArea.right, resultBottom);
        } else if (originalRatio > MAX_CONTROL_RATIO) {
            float resultHeight = mainArea.width() * MAX_CONTROL_RATIO;
            float resultWidth = originalRect.width() * resultHeight / originalRect.height();

            float resultBottom = mainArea.top + resultHeight;
            float resultLeft = mainArea.centerX() - resultWidth / 2;
            float resultRight = mainArea.centerX() + resultWidth / 2;
            resultRect = new RectF(resultLeft, mainArea.top, resultRight, resultBottom);
        } else {
            float resultHeight = mainArea.width() * MIN_CONTROL_RATIO;
            resultRect = new RectF(mainArea.left, mainArea.top, mainArea.right, mainArea.top + resultHeight);
        }

        resultHeight = (int) resultRect.height();
        List<RectF> resultList = new ArrayList<>();
        resultList.add(resultRect);
        return resultList;
    }

    private List<RectF> definePlacesForTwo(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForThree(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForFour(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForFive(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForSix(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForSeven(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForEight(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForNine(RectF mainArea) {
        return null;
    }

    private List<RectF> definePlacesForTen(RectF mainArea) {
        return null;
    }
}
