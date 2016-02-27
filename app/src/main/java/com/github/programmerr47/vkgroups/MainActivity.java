package com.github.programmerr47.vkgroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.programmerr47.vkgroups.pager.VkPagerAdapter;
import com.github.programmerr47.vkgroups.pager.pages.EventListPage;
import com.github.programmerr47.vkgroups.pager.pages.GroupListPage;
import com.github.programmerr47.vkgroups.pager.pages.Page;
import com.github.programmerr47.vkgroups.pager.pages.RecommendationsPage;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityCallbacks {

    private VkPagerAdapter pagerAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (VKSdk.isLoggedIn()) {
            init();
        } else {
            VKSdk.login(this, VKScope.GROUPS, VKScope.FRIENDS);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mainFragment.hasBackStack()) {
                mainFragment.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Page drawerPage = getDrawerPage(id);

        if (drawerPage != null && mainFragment.getOpenedPage().getClass() != drawerPage.getClass()) {
            mainFragment.openPage(drawerPage);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(navigationView);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                init();
            }
            @Override
            public void onError(VKError error) {
                if (error.errorCode == VKError.VK_CANCELED) {
                    finish();
                } else {
                    //TODO
                    throw new IllegalStateException("Have now behaviour for error: " + error);
                }
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void lockDrawer() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        }

        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED);
    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,/* toolbar,*/ R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_communities);

        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.main_fragment);

//        pager = (ViewPager) findViewById(R.id.main_pager);
//        pager.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        pager.setPageTransformer(false, new VkPagerTransformer());
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            private boolean isScrolledToPage;
//
//            @Override
//            public void onPageScrolled(int i, float v, int i2) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                if (i > 0) {
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                } else {
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                }
//
//                isScrolledToPage = true;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//                if (i == ViewPager.SCROLL_STATE_IDLE) {
//                    if (pager.getCurrentItem() < pagerAdapter.getCount() - 1) {
//                        pagerAdapter.removeLast();
//                    } else {
//                        if (isScrolledToPage) {
//                            int currentItemPos = pager.getCurrentItem();
////                            pagerAdapter.getFragmentList().get(currentItemPos).onPageOpened();
//                        } else {
//                            isScrolledToPage = false;
//                        }
//                    }
//                }
//            }
//        });
//        slowDownPager();
//
//        pagerAdapter = new VkPagerAdapter(fm, groupsFragment);
//        pager.setAdapter(pagerAdapter);
    }

    private Page getDrawerPage(int menuItemId) {
        switch (menuItemId) {
            case R.id.nav_communities:
                return new GroupListPage();
            case R.id.nav_events:
                return new EventListPage();
            case R.id.nav_recommendations:
                return new RecommendationsPage();
            default:
                return null;
        }
    }
}
