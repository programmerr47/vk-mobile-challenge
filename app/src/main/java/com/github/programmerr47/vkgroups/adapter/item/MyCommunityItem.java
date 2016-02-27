package com.github.programmerr47.vkgroups.adapter.item;

import android.text.TextUtils;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.utils.DateFormatter;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiCommunityFull;

import static com.github.programmerr47.vkgroups.utils.AndroidUtils.res;
import static com.vk.sdk.api.model.special.VkGroupType.EVENT;
import static com.vk.sdk.api.model.special.VkGroupType.PAGE;

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
        if (community.type == EVENT) {
            return DateFormatter.formatDate(community.start_date);
        } else {
            if (!TextUtils.isEmpty(community.activity)) {
                return community.activity;
            } else {
                return getLocalizedType(community);
            }
        }
    }

    private String getLocalizedType(VKApiCommunity community) {
        String localizedType = getLocalizedGroupClosedType(community) + " " + res().string(getLocalizedGroupTypeId(community));
        return localizedType.substring(0, 1).toUpperCase() + localizedType.substring(1);
    }

    private String getLocalizedGroupClosedType(VKApiCommunity community) {
        int closedTypeId = getLocalizedGroupClosedTypeId(community);

        if (closedTypeId != -1) {
            return res().string(closedTypeId);
        } else {
            return "";
        }
    }

    private int getLocalizedGroupClosedTypeId(VKApiCommunity community) {
        switch (community.is_closed) {
            case OPEN:
                if (community.type == PAGE) {
                    return R.string.publics;
                } else {
                    return R.string.open;
                }
            case CLOSED:
                return R.string.closed;
            case PRIVATE:
                return R.string.privates;
            default:
                return -1;
        }
    }

    private int getLocalizedGroupTypeId(VKApiCommunity community) {
        switch (community.type) {
            case PAGE:
                return R.string.page;
            case GROUP:
            default:
                return R.string.group;
        }
    }
}
