package com.github.programmerr47.vkgroups.pager.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.GroupAdapter;
import com.github.programmerr47.vkgroups.adapter.PostAdapter;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.item.CommunityItem;
import com.github.programmerr47.vkgroups.adapter.item.MyCommunityItem;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;
import com.github.programmerr47.vkgroups.background.db.GroupDao;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2/10/2016.
 */
public class GroupListPage extends Page implements View.OnClickListener {

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

    private final RecyclerView.RecycledViewPool postViewPool = new RecyclerView.RecycledViewPool();

    @Override
    public void onCreate() {
        myGroupItems = new RecyclerItems<>(new ArrayList<CommunityItem>());
        friendGroupItems = new RecyclerItems<>(new ArrayList<CommunityItem>());

        requestAllGroups();

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

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.community_layout, null, false);
    }

    @Override
    public void onViewCreated(View pageView) {
        appBarLayout = (AppBarLayout) pageView.findViewById(R.id.appbar_layout);
        toolbar = (Toolbar) pageView.findViewById(R.id.toolbar);
        spinner = (Spinner) pageView.findViewById(R.id.toolbar_spinner);
        createCommunityButton = (FloatingActionButton) pageView.findViewById(R.id.fab);
        myCommunityListView = (RecyclerView) pageView.findViewById(R.id.my_community_list);
        friendCommunityListView = (RecyclerView) pageView.findViewById(R.id.friend_community_list);

        prepareSpinner();
        prepareCreateCommunityButton();
        prepareItemsViews(pageView);
//        preparePostRecyclerPool(pageView.getContext());
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

    private void prepareItemsViews(View pageView) {
        myGroupAdapter = new GroupAdapter(myGroupItems, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myCommunityListView.getChildAdapterPosition(v);

                Snackbar.make(v, "Test grouplistpage click", Snackbar.LENGTH_SHORT);
                Page detailPage = new GroupDetailPage(myGroupItems.get(position).getCommunity(), postViewPool);
                pagerListener.openPage(detailPage);
//                Intent intent = new Intent(GroupsFragment.this.getContext(), TestDetailActivity.class);
//                intent.putExtra("TEST_IMAGE", myGroupItems.get(position).getCommunity().photo_200);
//                intent.putExtra("TEST_ID", myGroupItems.get(position).getCommunity().id);
//                startActivity(intent);
            }
        });

        myCommunityListView.setLayoutManager(new LinearLayoutManager(pageView.getContext()));
        myCommunityListView.setAdapter(myGroupAdapter);

        friendGroupAdapter = new GroupAdapter(friendGroupItems, this);

        friendCommunityListView.setLayoutManager(new LinearLayoutManager(pageView.getContext()));
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

    private void requestAllGroups() {
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
                Snackbar.make(createCommunityButton, error.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.try_again, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestAllGroups();
                            }
                        })
                        .show();
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

    private void preparePostRecyclerPool(Context context) {
        RecyclerView fakeRecyclerView = new RecyclerView(context);
        fakeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<PostItem> fakeItems = new ArrayList<>();
        for (int photoCount = 0; photoCount < 11; photoCount++) {
            fakeItems.add(new PostItem(createFakePost(photoCount), null, null, null));
        }

        PostAdapter fakeAdapter = new PostAdapter(fakeItems);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                PostItemHolder holder = fakeAdapter.createViewHolder(fakeRecyclerView, i);
                postViewPool.putRecycledView(holder);
            }
        }
    }

    private VKApiPost createFakePost(int photoCount) {
        VKApiPost apiPost = new VKApiPost();
        apiPost.text = "";
        apiPost.copy_history = new VKList<>();
        for (int i = 0; i < photoCount; i++) {
            apiPost.attachments.add(new VKApiPhoto());
        }
        return apiPost;
    }
}
