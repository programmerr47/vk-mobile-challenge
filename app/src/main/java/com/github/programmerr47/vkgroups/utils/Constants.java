package com.github.programmerr47.vkgroups.utils;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class Constants {

    public enum Font {
        ROBOTO_BOLD("Roboto-Bold.ttf"),
        ROBOTO_MEDIUM("Roboto-Medium.ttf"),
        ROBOTO_REGULAR("Roboto-Regular.ttf");

        private String fontName;

        private Font(String fontName) {
            this.fontName = fontName;
        }

        public String getFontName() {
            return fontName;
        }
    }

    private Constants() {}

    public static final String ASSETS_FONTS_DIR = "fonts/";
}
