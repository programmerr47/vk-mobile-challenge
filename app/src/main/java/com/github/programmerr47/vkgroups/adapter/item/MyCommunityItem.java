package com.github.programmerr47.vkgroups.adapter.item;

import com.vk.sdk.api.model.VKApiCommunityFull;

/**
 * @author Michael Spitsin
 * @since 2016-01-20
 */
public final class MyCommunityItem extends CommunityItem {

    public MyCommunityItem(VKApiCommunityFull community) {
        super(community);
    }

    @Override
    protected String getTextForSubInfoView() {
        return community.activity;
    }
}
