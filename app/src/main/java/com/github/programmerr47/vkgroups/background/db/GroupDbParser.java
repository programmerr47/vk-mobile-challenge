package com.github.programmerr47.vkgroups.background.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.github.programmerr47.vkgroups.background.db.entries.GroupEntry;
import com.github.programmerr47.vkgroups.background.objects.special.MemberStatus;
import com.vk.sdk.api.model.VKApiCity;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiCountry;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiPlace;
import com.vk.sdk.api.model.special.VkAdminLevel;
import com.vk.sdk.api.model.special.VkClosedType;
import com.vk.sdk.api.model.special.VkGroupType;

import static com.github.programmerr47.vkgroups.background.objects.special.MemberStatus.NOT_MEMBER;
import static com.vk.sdk.api.model.special.VkAdminLevel.NO;

/**
 * @author Michael Spitsin
 * @since 2015-01-13
 */
public class GroupDbParser {

    public VKApiCommunityFull fromCursor(Cursor cursor) {
        CursorHolder cursorHolder = new CursorHolder(cursor);
        VKApiCommunityFull group = new VKApiCommunityFull();
        group.id = cursorHolder.getInt(GroupEntry.GROUP_ID);
        group.name = cursorHolder.getString(GroupEntry.NAME);
        group.screen_name = cursorHolder.getString(GroupEntry.SCREEN_NAME);
        group.is_closed = VkClosedType.fromId(cursorHolder.getInt(GroupEntry.IS_CLOSED));
        group.admin_level = VkAdminLevel.fromId(cursorHolder.getInt(GroupEntry.ADMIN_LEVEL));
        group.is_admin = group.admin_level != NO;
        group.is_member = MemberStatus.fromId(cursorHolder.getInt(GroupEntry.MEMBER_STATUS)) != NOT_MEMBER;
        group.type = VkGroupType.fromId(cursorHolder.getInt(GroupEntry.GROUP_TYPE));

        group.photo_50 = cursorHolder.getString(GroupEntry.PHOTO_50_URI);
        if (!TextUtils.isEmpty(group.photo_50)) {
            group.photo.add(VKApiPhotoSize.create(group.photo_50, 50));
        }

        group.photo_100 = cursorHolder.getString(GroupEntry.PHOTO_100_URI);
        if (!TextUtils.isEmpty(group.photo_100)) {
            group.photo.add(VKApiPhotoSize.create(group.photo_100, 100));
        }

        group.photo_200 = cursorHolder.getString(GroupEntry.PHOTO_MAX_URI);
        if (!TextUtils.isEmpty(group.photo_200)) {
            group.photo.add(VKApiPhotoSize.create(group.photo_200, 200));
        }
        group.photo.sort();

        group.city = new VKApiCity();
        group.city.id = cursorHolder.getInt(GroupEntry.CITY_ID);

        group.country = new VKApiCountry();
        group.country.id = cursorHolder.getInt(GroupEntry.COUNTRY_ID);

        group.place = new VKApiPlace();
        group.place.id = cursorHolder.getInt(GroupEntry.PLACE_ID);

        group.description = cursorHolder.getString(GroupEntry.DESCRIPTION);
        group.wiki_page = cursorHolder.getString(GroupEntry.WIKI_PAGE);
        group.members_count = cursorHolder.getInt(GroupEntry.MEMBER_COUNT);

        group.counters = new VKApiCommunityFull.Counters();
        group.counters.photos = cursorHolder.getInt(GroupEntry.PHOTO_COUNT);
        group.counters.videos = cursorHolder.getInt(GroupEntry.VIDEO_COUNT);
        group.counters.audios = cursorHolder.getInt(GroupEntry.AUDIO_COUNT);
        group.counters.albums = cursorHolder.getInt(GroupEntry.ALBUM_COUNT);
        group.counters.topics = cursorHolder.getInt(GroupEntry.TOPIC_COUNT);
        group.counters.docs = cursorHolder.getInt(GroupEntry.DOC_COUNT);

        group.start_date = cursorHolder.getLong(GroupEntry.START_DATE);
        group.end_date = cursorHolder.getLong(GroupEntry.FINISH_DATE);
        group.can_post = cursorHolder.getInt(GroupEntry.CAN_POST) == 1;
        group.can_see_all_posts = cursorHolder.getInt(GroupEntry.CAN_SEE_ALL_POSTS) == 1;
        group.status = cursorHolder.getString(GroupEntry.STATUS);
        group.fixed_post = cursorHolder.getInt(GroupEntry.FIXED_POST_ID);
        group.verified = cursorHolder.getInt(GroupEntry.VERIFIED) == 1;
        group.site = cursorHolder.getString(GroupEntry.SITE);

        return group;
    }

    public ContentValues toContentValues(VKApiCommunityFull group) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GroupEntry.GROUP_ID, group.id);
        contentValues.put(GroupEntry.NAME, group.name);
        contentValues.put(GroupEntry.PHOTO_50_URI, group.photo_50);
        contentValues.put(GroupEntry.PHOTO_100_URI, group.photo_100);
        contentValues.put(GroupEntry.PHOTO_MAX_URI, group.photo_200);
        contentValues.put(GroupEntry.ACTIVITY, group.activity);
        return contentValues;
    }
}
