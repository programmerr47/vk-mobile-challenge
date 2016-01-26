package com.github.programmerr47.vkgroups.collections;

import android.support.annotation.NonNull;

import com.github.programmerr47.vkgroups.adapter.holder.producer.PostItemHolderProducer;
import com.github.programmerr47.vkgroups.adapter.item.PostItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Spitsin
 * @since 2016-01-25
 */
public class PostItems implements List<PostItem> {

    /**
     * Base object, list of items, that needed to be wrapped to providing determination of types.
     */
    private List<PostItem> items;

    private Map<Integer, PostItemHolderProducer> holderMap;
    //TODO think about replacement to map in order to remove binding position
    //TODO of item and type identificator, because it may be dangerous.
    private List<String> mTypeNames;

    public PostItems(@NonNull List<PostItem> items) {
        this.items = items;
        mTypeNames = getAllDifferentIdsFromPostItems(items);
        holderMap = retrieveProducers(items, mTypeNames);
    }

    @Override
    public void add(int location, PostItem item) {
        items.add(location, item);
        checkAndAddNewType(item, mTypeNames, holderMap);
    }

    @Override
    public boolean add(PostItem item) {
        boolean result = items.add(item);
        checkAndAddNewType(item, mTypeNames, holderMap);
        return result;
    }

    @Override
    public boolean addAll(int location, @NonNull Collection<? extends PostItem> collection) {
        boolean result = items.addAll(location, collection);

        for (PostItem item : collection) {
            checkAndAddNewType(item, mTypeNames, holderMap);
        }

        return result;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends PostItem> collection) {
        boolean result = items.addAll(collection);

        for (PostItem item : collection) {
            checkAndAddNewType(item, mTypeNames, holderMap);
        }

        return result;
    }

    //TODO add addAll(RecyclerItem) for better performance

    @Override
    public void clear() {
        items.clear();
        mTypeNames.clear();
        holderMap.clear();
    }

    @Override
    public boolean contains(Object object) {
        return items.contains(object);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return items.containsAll(collection);
    }

    @Override
    public PostItem get(int location) {
        return items.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return items.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<PostItem> iterator() {
        return items.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return items.lastIndexOf(object);
    }

    @NonNull
    @Override
    public ListIterator<PostItem> listIterator() {
        return items.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<PostItem> listIterator(int location) {
        return items.listIterator(location);
    }

    @Override
    public PostItem remove(int location) {
        //TODO
        return items.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        //TODO
        return items.remove(object);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException("removeAll not supported yet in RecyclerItems");
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException("retainAll not supported yet in RecyclerItems");
    }

    @Override
    public PostItem set(int location, PostItem object) {
        //TODO
        return items.set(location, object);
    }

    @Override
    public int size() {
        return items.size();
    }

    @NonNull
    @Override
    public PostItems subList(int start, int end) {
        throw new UnsupportedOperationException("subList not supported yet in RecyclerItems");
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] array) {
        return items.toArray(array);
    }

    @Override
    public String toString() {
        return items.toString();
    }

    public List<PostItem> getOriginalList(){
        return items;
    }

    public Map<Integer, PostItemHolderProducer> getTypesMap() {
        return Collections.unmodifiableMap(holderMap);
    }

    public int getItemType(int position) {
        PostItem item = get(position);
        String itemId = item.getItemId();
        return mTypeNames.indexOf(itemId);
    }

    private List<String> getAllDifferentIdsFromPostItems(Collection<PostItem> collection) {
        if (collection == null) {
            return null;
        } else {
            Set<String> preResult = new HashSet<>();

            for (PostItem item : collection) {
                String className = item.getItemId();
                preResult.add(className);
            }

            return new ArrayList<>(preResult);
        }
    }

    private Map<Integer, PostItemHolderProducer> retrieveProducers(List<PostItem> items, List<String> typeNames) {
        Map<Integer, PostItemHolderProducer> result = new HashMap<>();

        for (PostItem item : items) {
            String itemId = item.getItemId();

            int key = typeNames.indexOf(itemId);

            if (key == -1) {
                throw new IllegalArgumentException("Not filled collection of typeNames");
            } else if (!result.containsKey(key)) {
                result.put(key, item.getViewHolderProducer());
            }
        }

        return result;
    }

    private void checkAndAddNewType(PostItem item, List<String> typeNames, Map<Integer, PostItemHolderProducer> holderMap) {
        String typeName = item.getItemId();
        int key = typeNames.indexOf(typeName);

        if (key == -1) {
            typeNames.add(typeName);
            holderMap.put(typeNames.size() - 1, item.getViewHolderProducer());
        }
    }
}
