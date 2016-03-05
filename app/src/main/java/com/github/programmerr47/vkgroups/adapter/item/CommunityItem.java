package com.github.programmerr47.vkgroups.adapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.CommunityItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.HolderProducer;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.vk.sdk.api.model.VKApiCommunityFull;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-13
 */
public abstract class CommunityItem implements AdapterItem {

    protected VKApiCommunityFull community;

    public CommunityItem(VKApiCommunityFull community) {
        this.community = community;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder viewHolder, int position) {
        CommunityItemHolder holder = (CommunityItemHolder) viewHolder;
        bindView(holder, position);
    }

    @Override
    public HolderProducer getViewHolderProducer() {
        return new HolderProducer() {
            @Override
            public RecyclerView.ViewHolder produce(ViewGroup parentView) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
                View view = layoutInflater.inflate(getLayoutId(), parentView, false);

                if (view == null) {
                    throw new IllegalStateException("View not created");
                }

                return createHolder(view);
            }
        };
    }

    public VKApiCommunityFull getCommunity() {
        return community;
    }

    protected void bindView(CommunityItemHolder viewHolder, int position) {
        getImageWorker().loadImage(
                community.photo_100,
                viewHolder.getAvatarView(),
                new ImageWorker.LoadBitmapParams(100, 100));

        viewHolder.getTitleView().setText(community.name);
        viewHolder.getSubInfoView().setText(getTextForSubInfoView());
    }

    protected abstract String getTextForSubInfoView();

    protected int getLayoutId() {
        return R.layout.community_item;
    }

    protected CommunityItemHolder createHolder(View view) {
        CommunityItemHolder.ResourceParams params = new CommunityItemHolder.ResourceParams();
        params.avatarId = R.id.icon;
        params.titleId = R.id.title;
        params.subInfoId = R.id.type;

        return new CommunityItemHolder(view, params);
    }
}
