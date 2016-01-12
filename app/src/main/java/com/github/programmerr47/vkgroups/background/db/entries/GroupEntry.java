package com.github.programmerr47.vkgroups.background.db.entries;

import android.provider.BaseColumns;

import java.util.concurrent.TimeoutException;

import static com.github.programmerr47.vkgroups.background.db.entries.VkContract.COMMA_SEP;
import static com.github.programmerr47.vkgroups.background.db.entries.VkContract.INTEGER_TYPE;
import static com.github.programmerr47.vkgroups.background.db.entries.VkContract.TEXT_TYPE;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public class GroupEntry implements BaseColumns {

    public static final String TABLE_NAME = "group";

    public static final String GROUP_ID = "group_id";
    public static final String NAME = "name";
    public static final String SCREEN_NAME = "screen_name";
    public static final String IS_CLOSED = "is_closed";
    public static final String DEACTIVATED = "deactivated";
    public static final String ADMIN_LEVEL = "admin_level";
    public static final String MEMBER_STATUS = "member_status";
    public static final String INVITED_BY = "invited_by";
    public static final String GROUP_TYPE = "group_type";
    public static final String HAS_PHOTO = "has_photo";
    public static final String PHOTO_50_URI = "photo_50_uri";
    public static final String PHOTO_100_URI = "photo_100_uri";
    public static final String PHOTO_MAX_URI = "photo_max_uri";
    public static final String BAN_END_DATE = "ban_end_date";
    public static final String BAN_COMMENT = "ban_comment";
    public static final String CITY_ID = "city_id";
    public static final String COUNTRY_ID = "country_id";
    public static final String PLACE_ID = "place_id";
    public static final String DESCRIPTION = "description";
    public static final String WIKI_PAGE = "wiki_page";
    public static final String MEMBER_COUNT = "member_count";
    public static final String PHOTO_COUNT = "photo_count";
    public static final String VIDEO_COUNT = "video_count";
    public static final String AUDIO_COUNT = "audio_count";
    public static final String ALBUM_COUNT = "album_count";
    public static final String TOPIC_COUNT = "topic_count";
    public static final String DOC_COUNT = "doc_count";
    public static final String START_DATE = "start_date";
    public static final String FINISH_DATE = "finish_date";
    public static final String PUBLIC_START_DATE = "public_start_date";
    public static final String CAN_POST = "can_post";
    public static final String CAN_SEE_ALL_POSTS = "can_see_all_posts";
    public static final String CAN_UPLOAD_DOC = "can_upload_doc";
    public static final String CAN_UPLOAD_VIDEO = "can_upload_video";
    public static final String CAN_CREATE_TOPIC = "can_create_topic";
    public static final String ACTIVITY = "activity";
    public static final String STATUS = "status";
    public static final String FIXED_POST_ID = "fixed_post_id";
    public static final String VERIFIED = "verified";
    public static final String SITE = "site";
    public static final String MAIN_ALBUM_ID = "main_album_id";
    public static final String IS_FAVOURITE = "is_favourite";
    public static final String IS_HIDDEN_FROM_FEED = "is_hidden_from_feed";
    public static final String MAIN_SECTION = "main_section";

    static final String SQL_CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    GROUP_ID + INTEGER_TYPE + COMMA_SEP +
                    NAME + TEXT_TYPE + COMMA_SEP +
                    SCREEN_NAME + TEXT_TYPE + COMMA_SEP +
                    IS_CLOSED + INTEGER_TYPE + COMMA_SEP +
                    DEACTIVATED + TEXT_TYPE + COMMA_SEP +
                    ADMIN_LEVEL + TEXT_TYPE + COMMA_SEP +
                    MEMBER_STATUS + INTEGER_TYPE + COMMA_SEP +
                    INVITED_BY + INTEGER_TYPE + COMMA_SEP +
                    GROUP_TYPE + TEXT_TYPE + COMMA_SEP +
                    HAS_PHOTO + INTEGER_TYPE + COMMA_SEP +
                    PHOTO_50_URI + TEXT_TYPE + COMMA_SEP +
                    PHOTO_100_URI + TEXT_TYPE + COMMA_SEP +
                    PHOTO_MAX_URI + TEXT_TYPE + COMMA_SEP +
                    BAN_END_DATE + INTEGER_TYPE + COMMA_SEP +
                    BAN_COMMENT + TEXT_TYPE + COMMA_SEP +
                    CITY_ID + INTEGER_TYPE + COMMA_SEP +
                    COUNTRY_ID + INTEGER_TYPE + COMMA_SEP +
                    PLACE_ID + INTEGER_TYPE + COMMA_SEP +
                    DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    WIKI_PAGE + TEXT_TYPE + COMMA_SEP +
                    MEMBER_COUNT + INTEGER_TYPE + COMMA_SEP +
                    PHOTO_COUNT + INTEGER_TYPE + COMMA_SEP +
                    VIDEO_COUNT + INTEGER_TYPE + COMMA_SEP +
                    ALBUM_COUNT + INTEGER_TYPE + COMMA_SEP +
                    AUDIO_COUNT + INTEGER_TYPE + COMMA_SEP +
                    TOPIC_COUNT + INTEGER_TYPE + COMMA_SEP +
                    DOC_COUNT + INTEGER_TYPE + COMMA_SEP +
                    START_DATE + INTEGER_TYPE + COMMA_SEP +
                    FINISH_DATE + INTEGER_TYPE + COMMA_SEP +
                    PUBLIC_START_DATE + INTEGER_TYPE + COMMA_SEP +
                    CAN_POST + INTEGER_TYPE + COMMA_SEP +
                    CAN_SEE_ALL_POSTS + INTEGER_TYPE + COMMA_SEP +
                    CAN_UPLOAD_VIDEO + INTEGER_TYPE + COMMA_SEP +
                    CAN_UPLOAD_DOC + INTEGER_TYPE + COMMA_SEP +
                    CAN_CREATE_TOPIC + INTEGER_TYPE + COMMA_SEP +
                    ACTIVITY + TEXT_TYPE + COMMA_SEP +
                    STATUS + TEXT_TYPE + COMMA_SEP +
                    FIXED_POST_ID + INTEGER_TYPE + COMMA_SEP +
                    VERIFIED + INTEGER_TYPE + COMMA_SEP +
                    SITE + TEXT_TYPE + COMMA_SEP +
                    MAIN_ALBUM_ID + INTEGER_TYPE + COMMA_SEP +
                    IS_FAVOURITE + INTEGER_TYPE + COMMA_SEP +
                    IS_HIDDEN_FROM_FEED + INTEGER_TYPE + COMMA_SEP +
                    MAIN_SECTION + INTEGER_TYPE + ")";

    private GroupEntry() {}
}
