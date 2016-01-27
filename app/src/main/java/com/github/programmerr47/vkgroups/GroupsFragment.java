package com.github.programmerr47.vkgroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.programmerr47.vkgroups.adapter.GroupAdapter;
import com.github.programmerr47.vkgroups.adapter.item.CommunityItem;
import com.github.programmerr47.vkgroups.adapter.item.FriendsCommunityItem;
import com.github.programmerr47.vkgroups.adapter.item.MyCommunityItem;
import com.github.programmerr47.vkgroups.background.db.GroupDao;
import com.github.programmerr47.vkgroups.background.parsers.UsersIdJsonParser;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-01-09
 */
public class GroupsFragment extends Fragment implements View.OnClickListener {

    private enum SpinnerItemType {
        MY_GROUP(0),
        FRIEND_GROUP(1);

        private int position;

        SpinnerItemType(int position) {
            this.position = position;
        }

        private static SpinnerItemType fromPosition(int position) {
            for (SpinnerItemType type : SpinnerItemType.values()) {
                if (type.position == position) {
                    return type;
                }
            }

            throw new IllegalArgumentException("There is no Spinner type for position: " + position);
        }
    }

    private Spinner spinner;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private FloatingActionButton createCommunityButton;

    private RecyclerView myCommunityListView;
    private GroupAdapter myGroupAdapter;
    private RecyclerItems<CommunityItem> myGroupItems;

    private RecyclerView friendCommunityListView;
    private GroupAdapter friendGroupAdapter;
    private RecyclerItems<CommunityItem> friendGroupItems;

    public static GroupsFragment createInstance() {
        return new GroupsFragment();
    }

    public GroupsFragment() {
        //Def counstr
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        myGroupItems = new RecyclerItems<>(new ArrayList<CommunityItem>());
        friendGroupItems = new RecyclerItems<>(new ArrayList<CommunityItem>());

        //TODO move it
        VKRequest vkRequest = VKApi.groups().get(VKParameters
                .from(
                        VKApiConst.EXTENDED, 1,
                        VKApiConst.FIELDS, "blacklisted,status_audio,city,country,place,description,wiki_page,members_count,counters,start_date,finish_date,can_post,can_see_all_posts,activity,status,contacts,links,fixed_post,verified,site,ban_info",
                        VKApiConst.FILTER, "groups,publics"));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
                GroupDao groupDao = new GroupDao();
                for (final VKApiCommunityFull group : communityArray) {
                    groupDao.saveGroup(group);
                    CommunityItem item = new MyCommunityItem(group);
                    myGroupItems.add(item);
                    myGroupAdapter.notifyItemInserted(myGroupItems.size() - 1);
                }
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });

        //TODO
//        final VKRequest vkFriendRequest = VKApi.friends().get(VKParameters.from());
//        vkFriendRequest.executeWithListener(new VKRequest.VKRequestListener() {
//            @Override
//            public void onComplete(final VKResponse response) {
//                super.onComplete(response);
//
//                final Map<Integer, FriendsCommunityItem> itemsViews = new HashMap<>();
//                final Map<Integer, VKApiCommunityFull> items = new HashMap<>();
//                final Map<Integer, Integer> counters = new HashMap<>();
//
//                List<Integer> friendIds = new UsersIdJsonParser().parse(response.json);
//                List<VKRequest> friendGroupRequests = new ArrayList<>();
//                for (Integer friendId : friendIds) {
//                    VKRequest vkRequest = VKApi.groups().get(VKParameters
//                            .from(
//                                    VKApiConst.USER_ID, friendId,
//                                    VKApiConst.EXTENDED, 1,
//                                    VKApiConst.FIELDS, "blacklisted,status_audio,city,country,place,description,wiki_page,members_count,counters,start_date,finish_date,can_post,can_see_all_posts,activity,status,contacts,links,fixed_post,verified,site,ban_info",
//                                    VKApiConst.FILTER, "groups,publics"));
//                    vkRequest.setRequestListener(new VKRequest.VKRequestListener() {
//                        @Override
//                        public void onComplete(VKResponse response) {
//                            super.onComplete(response);
//                            VKApiCommunityArray communityArray = (VKApiCommunityArray) response.parsedModel;
//                            GroupDao groupDao = new GroupDao();
//
////                            Collections.sort(communityItems, new Comparator<CommunityItem>() {
////                                @Override
////                                public int compare(CommunityItem lhs, CommunityItem rhs) {
////                                    int lhsCount = Integer.parseInt(lhs.getCommunity().activity);
////                                    int rhsCount = Integer.parseInt(rhs.getCommunity().activity);
////                                    return lhsCount < rhsCount ? 1 : lhsCount > rhsCount ? -1 : 0 ;
////                                }
////                            });
//
//                            if (communityArray != null) {
//                                for (VKApiCommunityFull group : communityArray) {
//                                    //groupDao.saveGroup(group);
//
//                                    if (items.containsKey(group.id)) {
//                                        counters.put(group.id, counters.get(group.id) + 1);
//
//                                        FriendsCommunityItem item = itemsViews.get(group.id);
//                                        item.addFriend();
//
//                                        int position = friendGroupItems.indexOf(item) - 1;
//                                        while (position >= 0 &&
//                                                counters.get(friendGroupItems.get(position).getCommunity().id) < counters.get(group.id)) {
//                                            position--;
//                                        }
//
//                                        position++;
//
//                                        int oldPosition = friendGroupItems.indexOf(item);
//                                        friendGroupItems.remove(item);
//                                        friendGroupItems.add(position, item);
//
//                                        Log.v("FUCK", "Item moved " + item.getCommunity());
//                                        friendGroupAdapter.notifyItemMoved(oldPosition, position);
//                                        friendGroupAdapter.notifyItemChanged(position);
//                                        friendCommunityListView.scrollToPosition(0);
//                                    } else {
//                                        items.put(group.id, group);
//                                        counters.put(group.id, 1);
//
//                                        FriendsCommunityItem item = new FriendsCommunityItem(group);
//                                        itemsViews.put(group.id, item);
//
//                                        friendGroupItems.add(item);
//                                        friendGroupAdapter.notifyItemInserted(friendGroupItems.size() - 1);
//                                    }
//                                }
//                            }
//                        }
//                    });
//
//                    friendGroupRequests.add(vkRequest);
//                }
//                VKBatchRequest getAllFriendGroupRequest = new VKBatchRequest(friendGroupRequests);
//                getAllFriendGroupRequest.executeStepByStep(new VKBatchRequest.VKBatchRequestListener() {
//                    @Override
//                    public void onComplete(VKResponse[] responses) {
//                        super.onComplete(responses);
//                        Toast.makeText(getContext(), "Friends groups generating finished", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//                super.attemptFailed(request, attemptNumber, totalAttempts);
//            }
//
//            @Override
//            public void onError(VKError error) {
//                super.onError(error);
//            }
//
//            @Override
//            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
//                super.onProgress(progressType, bytesLoaded, bytesTotal);
//            }
//        });
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
        createCommunityButton = (FloatingActionButton) view.findViewById(R.id.fab);
        myCommunityListView = (RecyclerView) view.findViewById(R.id.my_community_list);
        friendCommunityListView = (RecyclerView) view.findViewById(R.id.friend_community_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareSpinner();
        prepareCreateCommunityButton();
        prepareItemsViews();
    }

    @Override
    public void onClick(View v) {

    }

    private void prepareSpinner(){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                toolbar.getContext(), R.array.communities_spinner_items, R.layout.spinner_title);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (SpinnerItemType.fromPosition(position)) {
                    case MY_GROUP:
                        myCommunityListView.setVisibility(View.VISIBLE);
                        friendCommunityListView.setVisibility(View.GONE);
                        createCommunityButton.show();
                        break;
                    case FRIEND_GROUP:
                        myCommunityListView.setVisibility(View.GONE);
                        friendCommunityListView.setVisibility(View.VISIBLE);
                        createCommunityButton.hide();
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void prepareItemsViews() {
        myGroupAdapter = new GroupAdapter(myGroupItems, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myCommunityListView.getChildAdapterPosition(v);

                Intent intent = new Intent(GroupsFragment.this.getContext(), TestDetailActivity.class);
                intent.putExtra("TEST_IMAGE", myGroupItems.get(position).getCommunity().photo_200);
                intent.putExtra("TEST_ID", myGroupItems.get(position).getCommunity().id);
                startActivity(intent);
            }
        });

        myCommunityListView.setLayoutManager(new LinearLayoutManager(getContext()));
        myCommunityListView.setAdapter(myGroupAdapter);

        friendGroupAdapter = new GroupAdapter(friendGroupItems, this);

        friendCommunityListView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendCommunityListView.setAdapter(friendGroupAdapter);

        switch (SpinnerItemType.fromPosition(spinner.getSelectedItemPosition())) {
            case MY_GROUP:
                myCommunityListView.setVisibility(View.VISIBLE);
                friendCommunityListView.setVisibility(View.GONE);
                break;
            case FRIEND_GROUP:
                myCommunityListView.setVisibility(View.GONE);
                friendCommunityListView.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }
}
