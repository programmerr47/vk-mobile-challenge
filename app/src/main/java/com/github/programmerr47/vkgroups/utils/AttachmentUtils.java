package com.github.programmerr47.vkgroups.utils;

import com.vk.sdk.api.model.VKAttachments;

import static com.vk.sdk.api.model.VKAttachments.Type.ALBUM;
import static com.vk.sdk.api.model.VKAttachments.Type.APP;
import static com.vk.sdk.api.model.VKAttachments.Type.PHOTO;
import static com.vk.sdk.api.model.VKAttachments.Type.POSTED_PHOTO;
import static com.vk.sdk.api.model.VKAttachments.Type.VIDEO;

/**
 * @author Michael Spitsin
 * @since 2016-02-23
 */
public class AttachmentUtils {

    public static boolean isAttachmentPhoto(VKAttachments.VKApiAttachment attachment) {
        return isAttachmentOneOf(attachment, PHOTO, POSTED_PHOTO, VIDEO, APP, ALBUM);
    }

    public static boolean isAttachmentOneOf(VKAttachments.VKApiAttachment attachment, VKAttachments.Type... types) {
        for (VKAttachments.Type type : types) {
            if (type == attachment.getType()) {
                return true;
            }
        }

        return false;
    }
}
