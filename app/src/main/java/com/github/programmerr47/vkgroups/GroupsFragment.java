package com.github.programmerr47.vkgroups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Michael Spitsin
 * @since 2016-01-09
 */
public class GroupsFragment extends Fragment {

    private RecyclerView communityList;
    private Spinner spinner;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private FloatingActionButton createCommunityButton;

    public static GroupsFragment createInstance() {
        return new GroupsFragment();
    }

    public GroupsFragment() {
        //Def counstr
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.community_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        spinner = (Spinner) view.findViewById(R.id.toolbar_spinner);
        communityList = (RecyclerView) view.findViewById(R.id.community_list);
        createCommunityButton = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareSpinner();
        prepareCreateCommunityButton();
    }

    private void prepareSpinner(){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                toolbar.getContext(), R.array.communities_spinner_items, R.layout.spinner_title);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void prepareCreateCommunityButton() {
        createCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}