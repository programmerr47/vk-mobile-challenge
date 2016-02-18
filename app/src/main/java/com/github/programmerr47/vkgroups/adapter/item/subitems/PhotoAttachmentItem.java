package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.vk.sdk.api.model.VKApiPhotoSize;

import static android.view.View.GONE;
import static com.github.programmerr47.vkgroups.ViewUtils.setVisibilityIfNeed;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class PhotoAttachmentItem extends PhotoSizeAttachmentItem {

    public PhotoAttachmentItem(VKApiPhotoSize photoSize, LayoutParams layoutParams) {
        super(photoSize, layoutParams);
    }

    @Override
    protected void bindAdditionalInfo(PhotoSizeAttachmentSubHolder holder) {
        setVisibilityIfNeed(holder.getBottomBackground(), GONE);
        setVisibilityIfNeed(holder.getBottomImagesIcon(), GONE);
        setVisibilityIfNeed(holder.getBottomOptionalInfo(), GONE);
        setVisibilityIfNeed(holder.getBottomTitle(), GONE);
        setVisibilityIfNeed(holder.getTopAppIcon(), GONE);
        setVisibilityIfNeed(holder.getTopBackground(), GONE);
        setVisibilityIfNeed(holder.getTopTitle(), GONE);
    }
}
