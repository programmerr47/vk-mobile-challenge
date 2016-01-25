package com.github.programmerr47.vkgroups.pager.pages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.programmerr47.vkgroups.R;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class GroupDetailPage {

    private View attachedView;

    public GroupDetailPage(Context context){
        attachedView = getViewByLayoutId(context, R.layout.group_page);
    }

    private View getViewByLayoutId(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public View getAttachedView() {
        return attachedView;
    }
}
