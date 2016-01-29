package com.github.programmerr47.vkgroups.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.R;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.github.programmerr47.vkgroups.AndroidUtils.res;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_BOLD;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_MEDIUM;
import static com.github.programmerr47.vkgroups.Constants.Font.ROBOTO_REGULAR;
import static com.github.programmerr47.vkgroups.ViewUtils.setCommonMargin;
import static com.github.programmerr47.vkgroups.ViewUtils.setMarginEndIfPossible;

/**
 * @author Michael Spitsin
 * @since 2016-01-25
 */
public class PostContentView extends LinearLayout {
    
    private ImageView ownerImageView;
    private CustomFontTextView ownerTitleView;
    private CustomFontTextView ownerPostDateView;
    private ImageView repostFlagView;

    private CustomFontTextView postTextView;
    private CustomFontTextView postExpandCollapseView;

    public PostContentView(Context context) {
        super(context);
        init();
    }

    public ImageView getOwnerImageView() {
        return ownerImageView;
    }

    public CustomFontTextView getOwnerTitleView() {
        return ownerTitleView;
    }

    public CustomFontTextView getOwnerPostDateView() {
        return ownerPostDateView;
    }

    public CustomFontTextView getPostTextView() {
        return postTextView;
    }

    public CustomFontTextView getPostExpandCollapseView() {
        return postExpandCollapseView;
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
        postHeaderTextContainer.setLayoutParams(postHeaderTextParams);

        LayoutParams wrapParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);

        LayoutParams ownerImageParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        setCommonMargin(ownerImageParams, res().dimenI(R.dimen.margin_medium));

        ownerImageView = new SquareImageView(getContext());
        ownerImageView.setLayoutParams(ownerImageParams);
        ownerImageView.setScaleType(CENTER_CROP);

        ownerTitleView = new CustomFontTextView(getContext());
        ownerTitleView.setFont(ROBOTO_MEDIUM);
        ownerTitleView.setLayoutParams(wrapParams);
        ownerTitleView.setTextSize(COMPLEX_UNIT_SP, 18);
        ownerTitleView.setTextColor(res().color(R.color.text_color_primary));
        ownerTitleView.setSingleLine();

        ownerPostDateView = new CustomFontTextView(getContext());
        ownerPostDateView.setFont(ROBOTO_REGULAR);
        ownerPostDateView.setLayoutParams(wrapParams);
        ownerPostDateView.setTextSize(COMPLEX_UNIT_SP, 16);
        ownerPostDateView.setTextColor(res().color(R.color.text_color_secondary));
        ownerPostDateView.setSingleLine();

        LayoutParams postTextParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setCommonMargin(postTextParams, res().dimenI(R.dimen.margin_medium));

        postTextView = new CustomFontTextView(getContext());
        postTextView.setFont(ROBOTO_REGULAR);
        postTextView.setLayoutParams(postTextParams);
        postTextView.setTextSize(COMPLEX_UNIT_SP, 16);
        postTextView.setTextColor(res().color(R.color.text_color_primary));

        LayoutParams postCollapseExpandViewParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        setCommonMargin(postCollapseExpandViewParams, res().dimenI(R.dimen.margin_medium));
        postCollapseExpandViewParams.gravity = Gravity.END;

        postExpandCollapseView = new CustomFontTextView(getContext());
        postExpandCollapseView.setFont(ROBOTO_BOLD);
        postExpandCollapseView.setLayoutParams(postTextParams);
        postExpandCollapseView.setTextSize(COMPLEX_UNIT_SP, 15);
        postExpandCollapseView.setTextColor(res().color(R.color.colorAccent));
        postExpandCollapseView.setAllCaps(true);

        postHeaderTextContainer.addView(ownerTitleView);
        postHeaderTextContainer.addView(ownerPostDateView);

        postHeaderContainer.addView(ownerImageView);
        postHeaderContainer.addView(postHeaderTextContainer);

        addView(postHeaderContainer);
        addView(postTextView);
        addView(postExpandCollapseView);
    }
}
