package com.github.programmerr47.vkgroups.adapter;

import android.support.annotation.NonNull;

import com.github.programmerr47.vkgroups.adapter.item.InviteItem;
import com.github.programmerr47.vkgroups.adapter.item.InviteItem.InviteItemCallbacks;
import com.github.programmerr47.vkgroups.collections.RecyclerItems;

import java.util.Collection;

/**
 * @author Mihail Spitsin
 * @since 2016-03-03
 */
public class InviteAdapter extends AbstractMultiTypeRecyclerAdapter<InviteItem> {

    private final InviteItemCallbacks callbacks;

    public InviteAdapter(@NonNull RecyclerItems<InviteItem> items, InviteItemCallbacks callbacks) {
        super(items);
        this.callbacks = callbacks;
        bindCallbacksToItems(items);
    }

    private void bindCallbacksToItems(Collection<InviteItem> items) {
        for (InviteItem item : items) {
            item.setItemCallbacks(callbacks);
        }
    }
}
