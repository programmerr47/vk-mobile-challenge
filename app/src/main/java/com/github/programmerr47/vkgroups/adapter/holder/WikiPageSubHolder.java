package com.github.programmerr47.vkgroups.adapter.holder;

import android.view.View;
import android.widget.TextView;

/**
 * @author Michael Spitsin
 * @since 04.02.2016
 */
public class WikiPageSubHolder {

    private TextView nameView;
    private TextView typeView;

    public WikiPageSubHolder(View view, ResourceParams params) {
        this.nameView = (TextView) view.findViewById(params.nameViewId);
        this.typeView = (TextView) view.findViewById(params.typeViewId);
    }

    public TextView getNameView() {
        return nameView;
    }

    public TextView getTypeView() {
        return typeView;
    }

    public static class ResourceParams {
        public int nameViewId;
        public int typeViewId;
    }
}
