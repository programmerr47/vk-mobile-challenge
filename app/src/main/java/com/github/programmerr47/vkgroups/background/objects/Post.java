package com.github.programmerr47.vkgroups.background.objects;

import com.github.programmerr47.vkgroups.background.objects.special.Attachment;
import com.github.programmerr47.vkgroups.background.objects.special.CommentsInfo;
import com.github.programmerr47.vkgroups.background.objects.special.Geo;
import com.github.programmerr47.vkgroups.background.objects.special.LikesInfo;
import com.github.programmerr47.vkgroups.background.objects.special.PostSource;
import com.github.programmerr47.vkgroups.background.objects.special.PostType;
import com.github.programmerr47.vkgroups.background.objects.special.RepostsInfo;

import java.util.Date;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-01-12
 */
public class Post {
    private long id;
    private long ownerId;
    private long fromId;
    private Date date;
    private long replyOwnerId;
    private long replyPostId;
    private boolean friendsOnly;
    private CommentsInfo commentsInfo;
    private LikesInfo likesInfo;
    private RepostsInfo repostsInfo;
    private PostType postType;
    private PostSource postSource;
    private List<Attachment> attachments;
    private Geo geo;
    private long signerId;
    private boolean canPin;
    private boolean isPinned;
    private List<Post> postHistory;
}
