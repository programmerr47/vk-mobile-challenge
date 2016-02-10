package com.github.programmerr47.vkgroups.pager.pages;

import android.content.Context;
import android.view.View;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public abstract class Page {

    private View pageView;

    public View createView(Context context) {
        pageView = onCreateView(context);
        onViewCreated(pageView);
        return pageView;
    }

    public void onCreate() {}

    public abstract View onCreateView(Context context);

    public void onViewCreated(View pageView) {

    }

    public void onResume() {}

    public void onPause() {}

    public void onDestroy() {}

    public View getPageView() {
        return pageView;
    }
}
