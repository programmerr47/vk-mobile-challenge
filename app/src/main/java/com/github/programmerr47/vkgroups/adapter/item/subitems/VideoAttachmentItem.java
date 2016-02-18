package com.github.programmerr47.vkgroups.adapter.item.subitems;

import android.view.ViewGroup;

import com.github.programmerr47.vkgroups.adapter.holder.PhotoSizeAttachmentSubHolder;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiVideo;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.programmerr47.vkgroups.DurationFormatter.formatDuration;
import static com.github.programmerr47.vkgroups.ViewUtils.setVisibilityIfNeed;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class VideoAttachmentItem extends PhotoSizeAttachmentItem {

    private final VKApiVideo vkApiVideo;

    public VideoAttachmentItem(VKApiVideo vkApiVideo, VKApiPhotoSize photoSize, ViewGroup.LayoutParams layoutParams) {
        super(photoSize, layoutParams);
        this.vkApiVideo = vkApiVideo;
    }

    @Override
    protected void bindAdditionalInfo(PhotoSizeAttachmentSubHolder holder) {
        setVisibilityIfNeed(holder.getBottomImagesIcon(), GONE);
        setVisibilityIfNeed(holder.getTopAppIcon(), GONE);
        setVisibilityIfNeed(holder.getTopBackground(), GONE);
        setVisibilityIfNeed(holder.getTopTitle(), GONE);

        setVisibilityIfNeed(holder.getBottomBackground(), VISIBLE);
        setVisibilityIfNeed(holder.getBottomOptionalInfo(), VISIBLE);
        setVisibilityIfNeed(holder.getBottomTitle(), VISIBLE);

        holder.getBottomTitle().setText(vkApiVideo.title);
        holder.getBottomOptionalInfo().setText(formatDuration(vkApiVideo.duration));
    }
}
