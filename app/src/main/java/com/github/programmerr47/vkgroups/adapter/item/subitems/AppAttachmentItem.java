package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.view.ViewGroup.LayoutParams;

import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.vk.sdk.api.model.VKApiApplicationContent;
import com.vk.sdk.api.model.VKApiPhotoSize;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.utils.ViewUtils.setVisibilityIfNeed;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class AppAttachmentItem extends PhotoSizeAttachmentItem {

    private final VKApiApplicationContent vkApiApp;

    public AppAttachmentItem(VKApiApplicationContent vkApiApp, VKApiPhotoSize photoSize, LayoutParams layoutParams) {
        super(photoSize, layoutParams);
        this.vkApiApp = vkApiApp;
    }

    @Override
    protected void bindAdditionalInfo(PhotoSizeAttachmentSubHolder holder) {
        setVisibilityIfNeed(holder.getBottomBackground(), GONE);
        setVisibilityIfNeed(holder.getBottomImagesIcon(), GONE);
        setVisibilityIfNeed(holder.getBottomOptionalInfo(), GONE);
        setVisibilityIfNeed(holder.getBottomTitle(), GONE);

        setVisibilityIfNeed(holder.getTopAppIcon(), VISIBLE);
        setVisibilityIfNeed(holder.getTopBackground(), VISIBLE);
        setVisibilityIfNeed(holder.getTopTitle(), VISIBLE);

        holder.getTopTitle().setText(vkApiApp.name);
    }
}
