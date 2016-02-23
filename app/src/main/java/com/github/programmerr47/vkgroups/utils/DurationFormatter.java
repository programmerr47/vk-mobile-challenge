package com.github.programmerr47.vkgroups.utils;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class DurationFormatter {

    private DurationFormatter() {}

    public static String formatDuration(int durSec) {
        int durMin = durSec / 60;
        int remSec = durSec - durMin * 60;
        String result = formatTwoDigitNumber(remSec);

        if (durMin > 0) {
            int durH = durMin / 60;
            int remMin = durMin - durH * 60;

            if (durH > 0) {
                return durH + ":" + formatTwoDigitNumber(remMin) + ":" + result;
            } else {
                return remMin + ":" + result;
            }
        } else {
            return "0:" + result;
        }

    }

    private static String formatTwoDigitNumber(int n) {
        return n > 10 ? String.valueOf(n) : "0" + n;
    }
}
