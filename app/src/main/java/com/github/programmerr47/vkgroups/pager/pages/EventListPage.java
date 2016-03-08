package com.github.programmerr47.vkgroups.pager.pages;

import com.github.programmerr47.vkgroups.R;

/**
 * @author Michael Spitsin
 * @since 2016-02-27
 */
public class EventListPage extends CommunityListPage {

    @Override
    protected int getSpinnerArrayId() {
        return R.array.events_spinner_items;
    }

    @Override
    protected String getCommunityFilter() {
        return "events";
    }
}
