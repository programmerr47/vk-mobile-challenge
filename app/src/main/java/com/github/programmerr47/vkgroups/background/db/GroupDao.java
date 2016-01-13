package com.github.programmerr47.vkgroups.background.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.programmerr47.vkgroups.background.db.entries.GroupEntry;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-01-13
 */
public class GroupDao {

    public void saveGroup(VKApiCommunityFull group) {
        SQLiteDatabase database = VkDbHelper.INSTANCE.getWritableDatabase();
        ContentValues contentValues = new GroupDbParser().toContentValues(group);

        if (hasEntry(database, group.id)) {
            database.update(
                    GroupEntry.TABLE_NAME,
                    contentValues,
                    GroupEntry.GROUP_ID + "=?",
                    new String[]{String.valueOf(group.id)}
            );
        } else {
            database.insert(
                    GroupEntry.TABLE_NAME,
                    null,
                    contentValues
            );
        }
    }

    private static boolean hasEntry(SQLiteDatabase database, int groupId) {
        Cursor cursor = database.query(
                GroupEntry.TABLE_NAME, null,
                GroupEntry.GROUP_ID + "=?",
                new String[] {String.valueOf(groupId)},
                null, null, null);

        boolean answer = false;
        if (cursor != null) {
            answer = cursor.moveToFirst();
            cursor.close();
        }
        return answer;
    }
}
