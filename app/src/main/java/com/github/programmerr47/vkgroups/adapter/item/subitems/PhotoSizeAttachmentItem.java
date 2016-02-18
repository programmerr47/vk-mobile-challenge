package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhotoSize;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getAppContext;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public abstract class PhotoSizeAttachmentItem {

    private final VKApiPhotoSize photoSize;
    private final LayoutParams photoSizeParams;

    public PhotoSizeAttachmentItem(VKApiPhotoSize photoSize) {
        this(photoSize, null);
    }

    public PhotoSizeAttachmentItem(VKApiPhotoSize photoSize, LayoutParams layoutParams) {
        this.photoSize = photoSize;
        this.photoSizeParams = layoutParams;
    }

    public final void bindView(PhotoSizeAttachmentSubHolder holder) {
        bindPhoto(holder);
        bindAdditionalInfo(holder);
    }

    protected abstract void bindAdditionalInfo(PhotoSizeAttachmentSubHolder holder);

    private void bindPhoto(PhotoSizeAttachmentSubHolder holder) {
        ImageView photo = holder.getPhoto();

        if (photoSizeParams != null) {
            photo.setLayoutParams(photoSizeParams);
        }

        Picasso.with(getAppContext()).load(photoSize.src).into(photo);
    }
}
