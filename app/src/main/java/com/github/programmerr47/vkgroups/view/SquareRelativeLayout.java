package com.github.programmerr47.vkgroups.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.github.programmerr47.vkgroups.R;
import com.github.programmerr47.vkgroups.view.params.DimensionType;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public class SquareRelativeLayout extends RelativeLayout {

    private DimensionType dimensionType = DimensionType.MIN;

    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = dimensionType.getSquareDimension(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(dimension, dimension);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SquareImageView);
            dimensionType = DimensionType.fromId(typedArray.getInt(R.styleable.SquareImageView_dimension, DimensionType.MIN.getId()));
            typedArray.recycle();
        }
    }
}
