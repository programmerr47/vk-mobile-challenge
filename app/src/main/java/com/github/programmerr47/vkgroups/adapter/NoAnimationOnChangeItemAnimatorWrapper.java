package com.github.programmerr47.vkgroups.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author Michael Spitsin
 * @since 26.01.2016
 */
public class NoAnimationOnChangeItemAnimatorWrapper extends RecyclerView.ItemAnimator {

    private RecyclerView.ItemAnimator mWrappedAnimator;

    public NoAnimationOnChangeItemAnimatorWrapper(@NonNull RecyclerView.ItemAnimator wrappedAnimator) {
        this.mWrappedAnimator = wrappedAnimator;
    }

    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return mWrappedAnimator.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return mWrappedAnimator.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return mWrappedAnimator.animatePersistence(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return true;
    }

    @Override
    public void runPendingAnimations() {
        mWrappedAnimator.runPendingAnimations();
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        mWrappedAnimator.endAnimation(item);
    }

    @Override
    public void endAnimations() {
        mWrappedAnimator.endAnimations();
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
