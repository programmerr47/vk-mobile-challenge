package com.github.programmerr47.vkgroups.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class PhotoSizeAttachmentSubHolder {

    private final View holderView;
    private final View topBackground;
    private final View bottomBackground;
    private final ImageView topAppIcon;
    private final ImageView bottomImagesIcon;
    private final TextView topTitle;
    private final TextView bottomTitle;
    private final TextView bottomOptionalInfo;
    private final ImageView photo;

    public PhotoSizeAttachmentSubHolder(View attachmentView, ResourceParams params) {
        holderView = attachmentView;
        topBackground = attachmentView.findViewById(params.topBackgroundId);
        bottomBackground = attachmentView.findViewById(params.bottomBackgroundId);
        topAppIcon = findViewById(attachmentView, params.topAppIconId);
        bottomImagesIcon = findViewById(attachmentView, params.bottomImagesIconId);
        topTitle = findViewById(attachmentView, params.topTitleId);
        bottomTitle = findViewById(attachmentView, params.bottomTitleId);
        bottomOptionalInfo = findViewById(attachmentView, params.bottomOptionalInfoId);
        photo = findViewById(attachmentView, params.photoId);
    }

    public View getHolderView() {
        return holderView;
    }

    public View getTopBackground() {
        return topBackground;
    }

    public View getBottomBackground() {
        return bottomBackground;
    }

    public ImageView getTopAppIcon() {
        return topAppIcon;
    }

    public ImageView getBottomImagesIcon() {
        return bottomImagesIcon;
    }

    public TextView getTopTitle() {
        return topTitle;
    }

    public TextView getBottomTitle() {
        return bottomTitle;
    }

    public TextView getBottomOptionalInfo() {
        return bottomOptionalInfo;
    }

    public ImageView getPhoto() {
        return photo;
    }

    @SuppressWarnings("unchecked")
    private static <T extends View> T findViewById(View root, int id) {
        return (T) root.findViewById(id);
    }

    public static final class ResourceParams {
        public int topBackgroundId;
        public int bottomBackgroundId;
        public int topAppIconId;
        public int bottomImagesIconId;
        public int topTitleId;
        public int bottomTitleId;
        public int bottomOptionalInfoId;
        public int photoId;
    }
}
