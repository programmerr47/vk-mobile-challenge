package com.github.programmerr47.vkgroups.adapter.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.adapter.holder.CommunityItemHolder;
import com.github.programmerr47.vkgroups.adapter.holder.producer.HolderProducer;
import com.vk.sdk.api.model.VKApiCommunityFull;

import java.net.URL;

/**
 * @author Michael Spitsin
 * @since 2016-01-13
 */
public class CommunityItem implements AdapterItem {

    private VKApiCommunityFull community;

    private CommunityItemHolder cachedHolder;

    public CommunityItem(VKApiCommunityFull community) {
        this.community = community;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder viewHolder, int position) {
        if (cachedHolder != null && cachedHolder == viewHolder) {
            bindView(cachedHolder, position);
        } else {
            CommunityItemHolder holder = (CommunityItemHolder) viewHolder;
            bindView(holder, position);
        }
    }

    private void bindView(CommunityItemHolder viewHolder, int position) {
//        viewHolder.getAvatarView().setImageURI(Uri.parse(community.photo_50));
        viewHolder.getTitleView().setText(community.name);
        viewHolder.getTypeView().setText(community.activity);
    }

    @Override
    public HolderProducer getViewHolderProducer() {
        return new HolderProducer() {
            @Override
            public RecyclerView.ViewHolder produce(ViewGroup parentView) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
                View view = layoutInflater.inflate(R.layout.community_item, parentView, false);

                if (view == null) {
                    throw new IllegalStateException("View not created");
                }

                CommunityItemHolder.ResourceParams params = new CommunityItemHolder.ResourceParams();
                params.avatarId = R.id.icon;
                params.titleId = R.id.title;
                params.typeId = R.id.type;

                cachedHolder = new CommunityItemHolder(view, params);
                return cachedHolder;
            }
        };
    }
}
