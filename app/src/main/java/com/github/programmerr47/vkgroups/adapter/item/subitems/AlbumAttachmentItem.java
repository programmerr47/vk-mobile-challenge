package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKApiPhotoSize;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.ViewUtils.setVisibilityIfNeed;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class AlbumAttachmentItem extends PhotoSizeAttachmentItem {

    private final VKApiPhotoAlbum vkApiAlbum;

    public AlbumAttachmentItem(VKApiPhotoAlbum vkApiAlbum, VKApiPhotoSize photoSize, ViewGroup.LayoutParams layoutParams) {
        super(photoSize, layoutParams);
        this.vkApiAlbum = vkApiAlbum;
    }

    @Override
    protected void bindAdditionalInfo(PhotoSizeAttachmentSubHolder holder) {
        setVisibilityIfNeed(holder.getTopAppIcon(), GONE);
        setVisibilityIfNeed(holder.getTopBackground(), GONE);
        setVisibilityIfNeed(holder.getTopTitle(), GONE);

        setVisibilityIfNeed(holder.getBottomBackground(), VISIBLE);
        setVisibilityIfNeed(holder.getBottomImagesIcon(), VISIBLE);
        setVisibilityIfNeed(holder.getBottomOptionalInfo(), VISIBLE);
        setVisibilityIfNeed(holder.getBottomTitle(), VISIBLE);

        holder.getBottomTitle().setText(vkApiAlbum.title);
        holder.getBottomTitle().setText(String.valueOf(vkApiAlbum.size));
    }
}
