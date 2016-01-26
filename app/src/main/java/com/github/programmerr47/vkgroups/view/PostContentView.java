package com.github.programmerr47.vkgroups.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.github.programmerr47.vkgroups.AndroidUtils.hasJellyBeanMr1;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_MEDIUM;

/**
 * @author Michael Spitsin
 * @since 2016-01-25
 */
public class PostContentView extends LinearLayout {
    
    private ImageView ownerImageView;
    private CustomFontTextView ownerTitleView;
    private CustomFontTextView ownerPostDateView;
    private ImageView repostFlagView;

    public PostContentView(Context context) {
        super(context);
        init();
    }

    public CustomFontTextView getOwnerTitleView() {
        return ownerTitleView;
    }

    private void init() {
        setOrientation(VERTICAL);

        LayoutParams postHeaderParams = new LayoutParams(MATCH_PARENT, res().dimenI(R.dimen.list_item_height_default));

        LinearLayout postHeaderContainer = new LinearLayout(getContext());
        postHeaderContainer.setOrientation(HORIZONTAL);
        postHeaderContainer.setLayoutParams(postHeaderParams);

        LayoutParams postHeaderTextParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        postHeaderTextParams.gravity = Gravity.CENTER;
        setMarginEndIfPossible(postHeaderTextParams, res().dimenI(R.dimen.margin_big));

        LinearLayout postHeaderTextContainer = new LinearLayout(getContext());
        postHeaderTextContainer.setOrientation(VERTICAL);
        postHeaderContainer.setLayoutParams(postHeaderTextParams);

        LayoutParams titleParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ownerTitleView = new CustomFontTextView(getContext());
        ownerTitleView.setFont(ROBOTO_MEDIUM);
        ownerTitleView.setLayoutParams(titleParams);
        ownerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ownerTitleView.setTextColor(res().color(R.color.text_color_primary));
        ownerTitleView.setSingleLine();

        postHeaderTextContainer.addView(ownerTitleView);
        postHeaderContainer.addView(postHeaderTextContainer);
    }

    private void setMarginEndIfPossible(LayoutParams layoutParams, int margin) {
        if (hasJellyBeanMr1()) {
            layoutParams.setMarginEnd(margin);
        } else {
            layoutParams.rightMargin = margin;
        }
    }
}