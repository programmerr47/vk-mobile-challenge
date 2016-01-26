package com.github.programmerr47.vkgroups.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.adapter.holder.producer.HolderProducer;
import com.github.programmerr47.vkgroups.adapter.item.AdapterItem;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-01-13
 */
public class AbstractMultiTypeRecyclerAdapter<Item extends AdapterItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected RecyclerItems<Item> mItems;

    public AbstractMultiTypeRecyclerAdapter(@NonNull RecyclerItems<Item> items) {
        mItems = items;
    }

    public AbstractMultiTypeRecyclerAdapter(@NonNull List<Item> items) {
        mItems = new RecyclerItems<>(items);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Map<Integer, HolderProducer> typeProducers = mItems.getTypesMap();
        HolderProducer producer = typeProducers.get(viewType);
        RecyclerView.ViewHolder result = producer.produce(parent);
        onPostCreateViewHolder(result);
        return result;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        long time = System.nanoTime();
        onPreBindViewHolder(holder, position);
        Item item = mItems.get(position);
        item.bindView(holder, position);
        onPostBindViewHolder(holder, position);
//        Log.v("FUCK", "onBind " + holder.getClass().getSimpleName() + ". Time = " + ((System.nanoTime() - time) / 1000.0));
    }

    //todo document
    protected void onPostCreateViewHolder(RecyclerView.ViewHolder holder) {
    }

    //todo document
    protected void onPostBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    //todo document
    protected void onPreBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public final int getItemCount() {
        return mItems.size();
    }

    @Override
    public final int getItemViewType(int position) {
        return mItems.getItemType(position);
    }

    public void updateItems(@NonNull RecyclerItems<Item> newItems) {
        mItems = newItems;
        notifyDataSetChanged();
    }

    public void addItem(@NonNull Item newItem) {
        addItem(mItems.size(), newItem);
    }

    public void addItem(int position, @NonNull Item newItem) {
        mItems.add(position, newItem);
        notifyItemInserted(position);
    }

    public void addItems(@NonNull Collection<? extends Item> newItems) {
        addItems(mItems.size(), newItems);
    }

    public void addItems(int position, @NonNull Collection<? extends Item> newItems) {
        mItems.addAll(position, newItems);
        notifyItemRangeInserted(position, newItems.size());
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public void notifyElementChange(Item item) {
        int position = mItems.indexOf(item);
        if (position != -1) {
            notifyItemChanged(position);
        }
    }
}
