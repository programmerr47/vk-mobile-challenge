package com.github.programmerr47.vkgroups.adapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.CommunityItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.InviteItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.InviteItemHolder.ResourceParams;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.special.VkGroupType;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Mihail Spitsin
 * @since 2016-03-03
 */
public class InviteItem extends MyCommunityItem {

    private InviteItemCallbacks itemCallbacks;

    public InviteItem(VKApiCommunityFull community) {
        super(community);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder viewHolder, int position) {
        bindView((InviteItemHolder) viewHolder, position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.invitation_item;
    }

    @Override
    protected CommunityItemHolder createHolder(View view) {
        ResourceParams params = new ResourceParams();
        params.avatarId = R.id.icon;
        params.titleId = R.id.title;
        params.subInfoId = R.id.type;
        params.contentViewId = R.id.community_info;
        params.notSureViewId = R.id.not_sure_button;
        params.acceptButtonId = R.id.accept_button;
        params.declineButtonId = R.id.decline_button;

        return new InviteItemHolder(view, params);
    }

    public void setItemCallbacks(InviteItemCallbacks callbacks) {
        this.itemCallbacks = callbacks;
    }

    private void bindView(InviteItemHolder viewHolder, int position) {
        bindView((CommunityItemHolder) viewHolder, position);

        viewHolder.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCallbacks != null) {
                    itemCallbacks.onInviteItemClick(v);
                }
            }
        });
        viewHolder.getAcceptButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCallbacks != null) {
                    itemCallbacks.onAcceptButtonClick(v);
                }
            }
        });
        viewHolder.getDeclineButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCallbacks != null) {
                    itemCallbacks.onDeclineButtonClick(v);
                }
            }
        });

        if (community.type == VkGroupType.EVENT) {
            viewHolder.getNotSureView().setVisibility(VISIBLE);
            viewHolder.getNotSureView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemCallbacks != null) {
                        itemCallbacks.onNotSureButtonClick(v);
                    }
                }
            });
        } else {
            viewHolder.getNotSureView().setVisibility(GONE);
        }
    }

    public interface InviteItemCallbacks {
        void onInviteItemClick(View v);
        void onNotSureButtonClick(View v);
        void onAcceptButtonClick(View v);
        void onDeclineButtonClick(View v);
    }
}
