package com.github.programmerr47.vkgroups.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.programmerr47.vkgroups.adapter.item.AdapterItem;
import com.github.programmerr47.vkgroups.adapter.item.AdapterItemNotifier;
import com.github.programmerr47.vkgroups.adapter.item.CommunityItem;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;

/**
 * @author Michael Spitsin
 * @since 2016-01-14
 */
public class GroupAdapter extends AbstractMultiTypeRecyclerAdapter<CommunityItem> implements AdapterItemNotifier {

    private final View.OnClickListener listener;

    public GroupAdapter(@NonNull RecyclerItems<CommunityItem> items, View.OnClickListener listener) {
        super(items);
        this.listener = listener;
    }

    @Override
    protected void onPostCreateViewHolder(RecyclerView.ViewHolder holder) {
        if (listener != null) {
            holder.itemView.setOnClickListener(listener);
        }
    }

    @Override
    public void notifyElementChanged(AdapterItem item) {
        //TODO
        int itemPosition = mItems.indexOf(item);
        if (itemPosition != -1) {
            notifyItemChanged(itemPosition);
        }
    }
}
