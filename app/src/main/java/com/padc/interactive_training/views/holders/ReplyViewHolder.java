package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ReplyVO;
import com.padc.interactive_training.utils.DateTimeUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class ReplyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.layout_like_button)
    LinearLayout layoutLikeButton;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_reply)
    TextView tvDescription;

    @BindView(R.id.tv_likes_count)
    TextView tvLikesCount;

    @BindView(R.id.tv_post_past_time)
    TextView tvPostPastTime;

    @BindView(R.id.tv_post_time)
    TextView tvPostTime;

    private Context mContext;
    private ReplyVO mReplyVO;
    private View mSelfView;

    public ReplyViewHolder(View itemView, ControllerReplyItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.mContext = InteractiveTrainingApp.getContext();
        this.mSelfView = itemView;
    }

    public void bindData(ReplyVO reply) {
        this.mReplyVO = reply;

        Date today = new Date();
        Date postDate = DateTimeUtils.parseStringToDateTime(reply.getReplyDateTime());

        tvPostPastTime.setText(DateTimeUtils.formattedDateDifference(postDate, today));
        tvPostTime.setText(DateTimeUtils.parseDateToString("hh:mm a", postDate));
        tvUserName.setText(reply.getUser().getFullName());
        tvDescription.setText(reply.getReply());
        tvLikesCount.setText("Like (" + String.valueOf(reply.getLikeCount()) + ")");
    }

    public interface ControllerReplyItem {
        void onTapReply(ReplyVO reply);
    }

}
