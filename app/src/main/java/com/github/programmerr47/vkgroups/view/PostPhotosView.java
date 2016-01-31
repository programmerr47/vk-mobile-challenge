package com.github.programmerr47.vkgroups.view;

import android.content.Context;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.AndroidUtils;
import com.github.programmerr47.vkgroups.PhotoViewPlaceDefiner;
import com.github.programmerr47.vkgroups.R;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * @author Michael Spitsin
 * @since 2016-01-30
 */
public class PostPhotosView extends ViewGroup {

    private int count;
    private PhotoViewPlaceDefiner definer;
    private List<ImageView> photoViews;

    public PostPhotosView(Context context, int count) {
        super(context);
        this.count = count;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int l = getPaddingRight();
        int r = widthSize - getPaddingLeft() - getPaddingRight();
        int t = getPaddingTop();
        int b = getPaddingTop();

        List<RectF> rects = definer.definePlaces(new RectF(l, t, r, b));

        for (int i = 0; i < rects.size(); i++) {
            photoViews.get(i).measure(
                    MeasureSpec.makeMeasureSpec((int) rects.get(i).width(), EXACTLY),
                    MeasureSpec.makeMeasureSpec((int) rects.get(i).height(), EXACTLY));
        }

        setMeasuredDimension(widthSize, definer.getResultHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        List<RectF> rects = definer.definePlaces(new RectF(l, t, r, b));

        for (int i = 0; i < rects.size(); i++) {
            RectF rect = rects.get(i);
            photoViews.get(i).layout((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
        }
    }

    public ImageView getPhoto(int location) {
        return photoViews.get(location);
    }

    public void refresh(List<VKApiPhoto> photos) {
        this.count = photos.size();
        definer = new PhotoViewPlaceDefiner(getPhotoRects(photos));
        init();
        invalidate();
        requestLayout();
    }

    private void init() {
        photoViews = initList();
        addViewsToView(photoViews);
    }

    private List<ImageView> initList() {
        List<ImageView> photoViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            photoViews.add(initImageView());
        }

        return photoViews;
    }

    private ImageView initImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(CENTER_CROP);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        imageView.setBackgroundColor(AndroidUtils.res().color(R.color.text_color_primary));
        return imageView;
    }

    private void addViewsToView(Collection<? extends View> views) {
        for (View view : views) {
            addView(view);
        }
    }

    private List<RectF> getPhotoRects(List<VKApiPhoto> photos) {
        List<RectF> result = new ArrayList<>();
        for (VKApiPhoto photo : photos) {
            result.add(new RectF(0, 0, photo.width, photo.height));
        }

        return result;
    }
}
