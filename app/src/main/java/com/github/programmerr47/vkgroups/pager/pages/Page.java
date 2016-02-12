package com.github.programmerr47.vkgroups.pager.pages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.github.programmerr47.vkgroups.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public abstract class Page {

    public static final int VEIL_ID = 1;

    private View pageView;
    protected PagerListener pagerListener;

    public View createView(Context context) {
        View originPageView = onCreateView(context);
        pageView = wrapPageView(context, originPageView);
        onViewCreated(originPageView);
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

    public void setPagerListener(PagerListener pagerListener) {
        this.pagerListener = pagerListener;
    }

    public boolean hasBackStack() {
        return false;
    }

    public void onBackPressed() {

    }

    private View wrapPageView(Context context, View pageView) {
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        FrameLayout wrapperLayout = new FrameLayout(context);
        wrapperLayout.setLayoutParams(params);

        View blackVeilView = new View(context);
        blackVeilView.setId(VEIL_ID);
        blackVeilView.setBackgroundColor(res().color(R.color.vk_black));
        blackVeilView.setAlpha(0.0f);

        wrapperLayout.addView(pageView);
        wrapperLayout.addView(blackVeilView);

        return wrapperLayout;
    }
}
