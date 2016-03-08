package com.github.programmerr47.vkgroups.pager.pages;

import android.view.View;

import com.github.programmerr47.vkgroups.R;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public class GroupListPage extends CommunityListPage {

    @Override
    protected void prepareToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerListener.openDrawer();
            }
        });
    }

    @Override
    protected int getSpinnerArrayId() {
        return R.array.groups_spinner_items;
    }

    @Override
    protected String getCommunityFilter() {
        return "groups,publics";
    }
}
