package com.github.programmerr47.vkgroups.background.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.programmerr47.vkgroups.background.db.entries.GroupEntry;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2015-01-13
 */
public class VkDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VkDbCache.db";

    public static final VkDbHelper INSTANCE = new VkDbHelper();

    private VkDbHelper() {
        super(getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GroupEntry.SQL_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
