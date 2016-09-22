package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.DiscussionVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class DiscussionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.layout_like_button)
    LinearLayout layoutLikeButton;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    private Context mContext;
    private DiscussionVO mDiscussionVO;
    private ControllerDiscussionItem mController;
    private View mSelfView;

    public DiscussionViewHolder(View itemView, ControllerDiscussionItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.mContext = InteractiveTrainingApp.getContext();
        this.mController = controller;
        this.mSelfView = itemView;

        Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.ic_face_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF9BBE74, PorterDuff.Mode.SRC_IN));
        this.ivProfile.setImageDrawable(mDrawable);

        setupClickableViews(mSelfView, mController);
    }

    private void setupClickableViews(View selfView, final ControllerDiscussionItem controller) {
        selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onTapDiscussion(mDiscussionVO);
            }
        });

        layoutLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onTapLikeButton(1);
            }
        });
    }

    public void bindData(DiscussionVO discussion) {
        this.mDiscussionVO = discussion;

    }

    public interface ControllerDiscussionItem {
        void onTapDiscussion(DiscussionVO discussion);
        void onTapLikeButton(Integer discussionID);
    }
}