package com.github.programmerr47.vkgroups;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Common presentation functions.
 *
 * @author Michael Spitsin
 * @since 2016-01-13
 */
public class Utils {

    /**
     * Retrieve {@link List} with names of all different realisations of some class/interface from
     * given collection.
     * <br><br>
     * <strong>For example:</strong> there is interface I, and implemented classes A, B, C, and
     * there is a {@code List<I>} that contains A, A, B, A. Then this function will return List with
     * two strings: name of class A and  name of class B, because in this list there are objects of
     * only two different classes.
     * <br><br>
     * <strong>Note that:</strong> return value is Map for better performance, because retrieving map element
     * is faster than, for example, list.indexOf.
     *
     * @param collection given collection of objects
     * @return List with names of different classes
     */
    public static List<String> getAllDifferentClassesFromCollection(Collection<?> collection) {
        if (collection == null) {
            return null;
        } else {
            Set<String> preResult = new HashSet<>();

            for (Object item : collection) {
                String className = item.getClass().getName();
                preResult.add(className);
            }

            return new ArrayList<>(preResult);
        }
    }

    public static int getMaxVisibleRowsInScreenRes(Context context, int minHeightOfOneItemRes) {
        float minHeightOfOneItem = context.getResources().getDimension(minHeightOfOneItemRes);
        return getMaxVisibleRowsInScreen(context, (int)minHeightOfOneItem);
    }

    public static int getMaxVisibleRowsInScreen(Context context, int minHeightOfOneItem) {
        int viewHeight = context.getResources().getDisplayMetrics().heightPixels;
        return (int)Math.ceil(viewHeight * 1.0 / minHeightOfOneItem) + 1;
    }

    public static String getAppropriateFileSizeString(int fileSizeInBytes) {
        if (fileSizeInBytes > 1000) {
            double fileSizeInKB = Math.round(fileSizeInBytes / 100) / 10.0;
            if (fileSizeInKB > 1000) {
                double fileSizeInMb = Math.round(fileSizeInKB / 100) / 10.0;
                return fileSizeInMb + " MB";
            } else {
                return fileSizeInKB + " KB";
            }
        } else {
            return fileSizeInBytes + " B";
        }
    }

    public static String getAppropriatePlaybackDuration(int durationInSeconds) {
        if (durationInSeconds > 59) {
            int minutes = durationInSeconds / 60;
            durationInSeconds = durationInSeconds % 60;
            return String.valueOf(minutes) + (durationInSeconds < 10 ? "0" + durationInSeconds : durationInSeconds);
        } else {
            return "0:" + (durationInSeconds < 10 ? "0" + durationInSeconds : durationInSeconds);
        }
    }

    public static String secondsToString(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
