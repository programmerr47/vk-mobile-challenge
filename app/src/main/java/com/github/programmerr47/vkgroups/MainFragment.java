package com.github.programmerr47.vkgroups;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.pager.FixedSpeedScroller;
import com.github.programmerr47.vkgroups.pager.VkPagerTransformer;
import com.github.programmerr47.vkgroups.pager.pages.GroupListPage;
import com.github.programmerr47.vkgroups.pager.pages.Page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public class MainFragment extends Fragment {

    private ViewPager pager;
    private VkPageAdapter adapter;
    private List<Page> pages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Page groupListPage = new GroupListPage();
        groupListPage.onCreate();
        pages.add(groupListPage);
        adapter = new VkPageAdapter(pages);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pager = (ViewPager) view.findViewById(R.id.main_pager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (Page page : pages) {
            page.createView(getActivity());
        }

        pager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        pager.setPageTransformer(false, new VkPagerTransformer());
        slowDownPager();
        pager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        pages.get(pages.size() - 1).onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pages.get(pages.size() - 1).onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (Page page : pages) {
            page.onDestroy();
        }
    }

    private void slowDownPager() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext());
            mScroller.set(pager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            //ignore
        }
    }

    public static final class VkPageAdapter extends PagerAdapter {

        private List<Page> pages;

        public VkPageAdapter(List<Page> pages) {
            this.pages = pages;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position){
            View v = pages.get(position).getPageView();
            collection.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            collection.removeView((View) view);
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount(){
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view.equals(object);
        }

        public Page getPage(int position) {
            return pages.get(position);
        }

        public void addPage(Page page) {
            pages.add(page);
            notifyDataSetChanged();
        }

        public void removePage(int position) {
            pages.remove(position);
            notifyDataSetChanged();
        }
    }
}
