package com.github.programmerr47.vkgroups.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.github.programmerr47.vkgroups.adapter.holder.CommunityItemHolder;
import com.github.programmerr47.vkgroups.utils.ViewUtils;

import static com.github.programmerr47.vkgroups.utils.ViewUtils.findViewById;

/**
 * @author Mihail Spitsin
 * @since 2016-03-03
 */
public class InviteItemHolder extends CommunityItemHolder {

    private final View contentView;
    private final TextView notSureView;
    private final View acceptButton;
    private final View declineButton;

    public InviteItemHolder(View itemView, ResourceParams params) {
        super(itemView, params);

        contentView = itemView.findViewById(params.contentViewId);
        notSureView = findViewById(itemView, params.notSureViewId);
        acceptButton = itemView.findViewById(params.acceptButtonId);
        declineButton = itemView.findViewById(params.declineButtonId);
    }

    public View getContentView() {
        return contentView;
    }

    public TextView getNotSureView() {
        return notSureView;
    }

    public View getAcceptButton() {
        return acceptButton;
    }

    public View getDeclineButton() {
        return declineButton;
    }

    public static class ResourceParams extends CommunityItemHolder.ResourceParams {
        public int contentViewId;
        public int notSureViewId;
        public int acceptButtonId;
        public int declineButtonId;
    }
}
