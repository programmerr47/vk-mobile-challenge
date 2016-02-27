package com.github.programmerr47.vkgroups.pager.pages;

/**
 * @author Michael Spitsin
 * @since 2016-02-27
 */
public class EventListPage extends CommunityListPage {
    @Override
    protected String getCommunityFilter() {
        return "events";
    }
}
