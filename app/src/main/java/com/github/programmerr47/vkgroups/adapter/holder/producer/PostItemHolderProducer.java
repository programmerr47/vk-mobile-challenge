package com.github.programmerr47.vkgroups.adapter.holder.producer;

import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.adapter.holder.PostItemHolder;

/**
 * @author Michael Spitsin
 * @since 2016-01-26
 */
public interface PostItemHolderProducer {
    PostItemHolder produce(ViewGroup parentView);
}
