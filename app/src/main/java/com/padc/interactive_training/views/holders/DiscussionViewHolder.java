package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.utils.DateTimeUtils;

import org.w3c.dom.Text;

import java.util.Date;

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

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_discussion_title)
    TextView tvDiscussionTitle;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.tv_likes_count)
    TextView tvLikesCount;

    @BindView(R.id.tv_reply_count)
    TextView tvReplyCount;

    @BindView(R.id.tv_post_past_time)
    TextView tvPostPastTime;

    @BindView(R.id.tv_post_time)
    TextView tvPostTime;

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

        Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.ic_face_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF9BBE74, PorterDuff.Mode.SRC_IN));
        this.ivProfile.setImageDrawable(mDrawable);

        Date today = new Date();
        Date postDate = DateTimeUtils.parseStringToDateTime(discussion.getPostDateTime());

        tvPostPastTime.setText(DateTimeUtils.formattedDateDifference(postDate, today));
        tvPostTime.setText(DateTimeUtils.parseDateToString("hh:mm a", postDate));

        tvUserName.setText(discussion.getUser().getFullName());
        tvDiscussionTitle.setText(discussion.getTitle());
        tvDescription.setText(discussion.getDescription());
        tvLikesCount.setText("Like (" + String.valueOf(discussion.getLikes()) + ")");
        tvReplyCount.setText("Replies (" + String.valueOf(discussion.getReplies().size()) + ")");

        setupClickableViews(mSelfView, mController);
    }

    public interface ControllerDiscussionItem {
        void onTapDiscussion(DiscussionVO discussion);
        void onTapLikeButton(Integer discussionID);
    }
}