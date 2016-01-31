package com.github.programmerr47.vkgroups.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.github.programmerr47.vkgroups.Constants;
import com.github.programmerr47.vkgroups.R;

/**
 * @author Michael Spitsin
 * @since 2016-01-08
 */
public class SquareImageView extends ImageView {

    private enum DimensionType {
        WIDTH(0) {
            @Override
            public int getSquareDimension(int viewWidth, int viewHeight) {
                return viewWidth;
            }
        },
        HEIGHT(1) {
            @Override
            public int getSquareDimension(int viewWidth, int viewHeight) {
                return viewHeight;
            }
        },
        MIN(2) {
            @Override
            public int getSquareDimension(int viewWidth, int viewHeight) {
                if (viewWidth != 0 && viewHeight != 0) {
                    return Math.min(viewWidth, viewHeight);
                } else if (viewWidth == 0) {
                    return viewHeight;
                } else {
                    return viewWidth;
                }
            }
        },
        MAX(3) {
            @Override
            public int getSquareDimension(int viewWidth, int viewHeight) {
                if (viewWidth != 0 && viewHeight != 0) {
                    return Math.max(viewWidth, viewHeight);
                } else if (viewWidth == 0) {
                    return viewHeight;
                } else {
                    return viewWidth;
                }
            }
        };

        private final int id;

        DimensionType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public abstract int getSquareDimension(int viewWidth, int viewHeight);

        public static DimensionType fromId(int id) {
            for (DimensionType dimensionType : DimensionType.values()) {
                if (dimensionType.id == id) {
                    return dimensionType;
                }
            }

            throw new IllegalArgumentException("There is not type for id: " + id);
        }
    }

    private DimensionType dimensionType = DimensionType.MIN;

    public SquareImageView(Context context) {
        super(context);
        init(null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            dimensionType = DimensionType.fromId(typedArray.getInt(R.styleable.SquareImageView_dimension, DimensionType.MIN.id));
            typedArray.recycle();
        }
    }
}
