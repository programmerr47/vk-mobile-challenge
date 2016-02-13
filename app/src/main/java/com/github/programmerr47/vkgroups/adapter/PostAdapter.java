package com.github.programmerr47.vkgroups.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.adapter.item.PostItemNotifier;
import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;

import java.util.Collection;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 26.01.2016
 */
public class PostAdapter extends RecyclerView.Adapter<PostItemHolder> implements PostItemNotifier {

    private List<PostItem> items;

    public PostAdapter(@NonNull List<PostItem> items) {
        this.items = items;
    }

    @Override
    public PostItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PostItem.createHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(PostItemHolder holder, int position) {
        onPreBindViewHolder(holder, position);
        PostItem item = items.get(position);
        item.bindView(holder, position);
        onPostBindViewHolder(holder, position);
    }

    //todo document
    protected void onPostCreateViewHolder(PostItemHolder holder) {
    }

    //todo document
    protected void onPostBindViewHolder(PostItemHolder holder, int position) {
    }

    //todo document
    protected void onPreBindViewHolder(PostItemHolder holder, int position) {
    }

    @Override
    public final int getItemCount() {
        return items.size();
    }

    @Override
    public final int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

    public void updateItems(@NonNull List<PostItem> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public void addItem(@NonNull PostItem newItem) {
        addItem(items.size(), newItem);
    }

    public void addItem(int position, @NonNull PostItem newItem) {
        items.add(position, newItem);
        notifyItemInserted(position);
    }

    public void addItems(@NonNull Collection<? extends PostItem> newItems) {
        addItems(items.size(), newItems);
    }

    public void addItems(int position, @NonNull Collection<? extends PostItem> newItems) {
        items.addAll(position, newItems);
        notifyItemRangeInserted(position, newItems.size());
    }

    public PostItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public void notifyItemChanged(PostItem item) {
        int position = items.indexOf(item);
        if (position != -1) {
            notifyItemChanged(position);
        }
    }
}
