package com.github.programmerr47.vkgroups.pager.pages;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public class GroupListPage extends CommunityListPage {

    @Override
    protected String getCommunityFilter() {
        return "groups,publics";
    }
}
