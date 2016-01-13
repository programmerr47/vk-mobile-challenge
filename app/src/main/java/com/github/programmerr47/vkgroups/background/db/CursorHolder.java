package com.github.programmerr47.vkgroups.background.db;

import android.database.Cursor;

/**
 * @author Michael Spitsin
 * @since 2015-01-13
 */
public class CursorHolder {

    private final Cursor cursor;

    public CursorHolder(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public String getString(String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public int getInt(String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public long getLong(String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
