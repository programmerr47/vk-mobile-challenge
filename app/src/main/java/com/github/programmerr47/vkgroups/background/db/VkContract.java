package com.github.programmerr47.vkgroups.background.db;

import android.provider.BaseColumns;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public class VkContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";

    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_NAME_BOOK_ID = "bookId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_SHORT_DESCR = "shortDescr";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_PAGE_COUNT = "pageCount";
        public static final String COLUMN_NAME_IMAGE_URL = "imageUrl";

        static final String SQL_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_NAME_BOOK_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_SHORT_DESCR + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_PAGE_COUNT + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_IMAGE_URL + TEXT_TYPE + ")";
    }

    public static abstract class BookSummaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "bookSummary";
        public static final String COLUMN_NAME_BOOK_ID = "bookId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_PAGE_COUNT = "pageCount";
        public static final String COLUMN_NAME_IMAGE_URL = "imageUrl";

        static final String SQL_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_NAME_BOOK_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_PAGE_COUNT + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_IMAGE_URL + TEXT_TYPE + ")";
    }
}
