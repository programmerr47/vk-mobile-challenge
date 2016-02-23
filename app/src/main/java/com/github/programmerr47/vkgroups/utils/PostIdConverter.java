package com.github.programmerr47.vkgroups.utils;

import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKAttachments.VKApiAttachment;

import static com.github.programmerr47.vkgroups.utils.AttachmentUtils.isAttachmentPhoto;

/**
 * @author Michael Spitsin
 * @since 2016-02-23
 */
public class PostIdConverter {

    public static int idFromPost(VKApiPost post) {
        PostCounters counters = PostCounters.fromPost(post);
        return counters.photoAttachments * 100 + counters.otherAttachments;
    }

    public static PostCounters postInfoFromId(int id) {
        return new PostCounters(id / 100, id % 100);
    }

    public static class PostCounters {
        public final int photoAttachments;
        public final int otherAttachments;

        private PostCounters(int photoAttachments, int otherAttachments) {
            this.photoAttachments = photoAttachments;
            this.otherAttachments = otherAttachments;
        }

        private static PostCounters fromPost(VKApiPost post) {
            int photoAttachments = 0;

            for (VKApiAttachment attachment : post.attachments) {
                if (isAttachmentPhoto(attachment)) {
                    photoAttachments++;
                }
            }

            return new PostCounters(photoAttachments, post.attachments.size() - photoAttachments);
        }
    }
}
