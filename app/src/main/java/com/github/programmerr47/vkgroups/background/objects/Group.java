package com.github.programmerr47.vkgroups.background.objects;

import com.github.programmerr47.vkgroups.background.objects.special.AdminLevel;
import com.github.programmerr47.vkgroups.background.objects.special.BanInfo;
import com.github.programmerr47.vkgroups.background.objects.special.City;
import com.github.programmerr47.vkgroups.background.objects.special.ClosedType;
import com.github.programmerr47.vkgroups.background.objects.special.Counters;
import com.github.programmerr47.vkgroups.background.objects.special.Country;
import com.github.programmerr47.vkgroups.background.objects.special.DeactivatedType;
import com.github.programmerr47.vkgroups.background.objects.special.GroupType;
import com.github.programmerr47.vkgroups.background.objects.special.MainSectionType;
import com.github.programmerr47.vkgroups.background.objects.special.Market;
import com.github.programmerr47.vkgroups.background.objects.special.MemberStatus;
import com.github.programmerr47.vkgroups.background.objects.special.Place;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.util.Date;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public class Group {
    private long id;
    private String name;
    private String screenName;
    private ClosedType isClosed;
    private DeactivatedType deactivated;
    private AdminLevel adminLevel;
    private MemberStatus memberStatus;
    private int invitedBy;
    private GroupType type;
    private boolean hasPhoto;
    private String photo50Uri;
    private String photo100Uri;
    private String photoMaxUri;
    private BanInfo banInfo;
    private City city;
    private Country country;
    private Place place;
    private String description;
    private String wikiPage;
    private int memberCount;
    private Counters counters;
    private Date startDate;
    private Date finishDate;
    private String publicStartDate;
    private boolean canPost;
    private boolean canSeeAllPosts;
    private boolean canUploadDoc;
    private boolean canUploadVideo;
    private boolean canCreateTopic;
    private String activity;
    private String status;
    private List<VKApiCommunityFull.Contact> contacts;
    private List<Group> links;
    private Post fixedPost;
    private boolean verified;
    private String site;
    private Album mainAlbum;
    private boolean isFavourite;
    private boolean isHiddenFromFeed;
    private MainSectionType mainSection;
    private Market market;
}
