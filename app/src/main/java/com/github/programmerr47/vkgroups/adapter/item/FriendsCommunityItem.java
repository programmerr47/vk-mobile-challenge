package com.github.programmerr47.vkgroups.adapter.item;

import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiCommunityFull;

import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2016-01-20
 */
public final class FriendsCommunityItem extends CommunityItem {

    private int friendsCount;

    private String cachedFriendCountString;

    public FriendsCommunityItem(VKApiCommunityFull community) {
        this(community, 1);
    }

    public FriendsCommunityItem(VKApiCommunityFull community, int friendsCount) {
        super(community);
        setFriendsCount(friendsCount);
    }

    public void addFriend() {
        setFriendsCount(friendsCount + 1);
    }

    @Override
    protected String getTextForSubInfoView() {
        return cachedFriendCountString;
    }

    private void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
        this.cachedFriendCountString = res().plural(R.plurals.friends, friendsCount, friendsCount);
    }
}
