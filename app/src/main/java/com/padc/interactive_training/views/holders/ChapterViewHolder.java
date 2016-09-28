package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ChapterVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 9/6/2016.
 */
public class ChapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_chapter_number)
    TextView tvChapterNumber;

    @BindView(R.id.tv_chapter_title)
    TextView tvChapterTitle;

    @BindView(R.id.tv_chapter_brief)
    TextView tvChapterBrief;

    @BindView(R.id.iv_lock)
    ImageView ivLock;

    @BindView(R.id.tv_lock_label)
    TextView tvLock;

    @BindView(R.id.tv_duration)
    TextView tvDuration;

    @BindView(R.id.tv_card_count)
    TextView tvCardCount;

    @BindView(R.id.tv_finished_percentage)
    TextView tvFinishedPercentage;

    @BindView(R.id.view_finished_percentage)
    View vFinished;

    @BindView(R.id.view_remaining_percentage)
    View vRemaining;

    private Context mContext;

    private ChapterVO mChapterVO;
    private ControllerChapterItem mController;
    private View mSelfView;

    public ChapterViewHolder(View itemView, ControllerChapterItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.mContext = InteractiveTrainingApp.getContext();
        this.mController = controller;
        this.mSelfView = itemView;
    }

    public void bindData(ChapterVO chapter) {
        this.mChapterVO = chapter;

        tvChapterNumber.setText("Chapter-" + String.valueOf(chapter.getChapterNumber()));
        tvChapterTitle.setText(chapter.getTitle());
        tvChapterBrief.setText(chapter.getChapterBrief());

        String durtionInMinute = tvDuration.getResources().getQuantityString(
                R.plurals.minutes_count, chapter.getDurationInMins(), chapter.getDurationInMins()
        );
        tvDuration.setText(durtionInMinute);

        String cardsCount = mContext.getResources().getQuantityString(
                R.plurals.cards_count, chapter.getLessonCount(), chapter.getLessonCount()
        );
        tvCardCount.setText(cardsCount);

        if (chapter.isLocked()) {
            // Define Colors for Disabled State
            tvChapterNumber.setTextColor(mContext.getResources().getColor(R.color.chapter_disabled_text_color));
            tvChapterTitle.setTextColor(mContext.getResources().getColor(R.color.chapter_disabled_text_color));
            tvChapterBrief.setTextColor(mContext.getResources().getColor(R.color.chapter_disabled_text_color));

            // Hide Duration and Cards Count
            tvDuration.setVisibility(View.INVISIBLE);
            tvCardCount.setVisibility(View.INVISIBLE);
            tvFinishedPercentage.setVisibility(View.INVISIBLE);

            // Define Lock Disabled Color
            vFinished.setBackgroundColor(mContext.getResources().getColor(R.color.chapter_disabled_background));
            vFinished.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 100));

            vRemaining.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 0));

        } else {
            // Show Duration and Cards Count
            tvDuration.setVisibility(View.VISIBLE);
            tvCardCount.setVisibility(View.VISIBLE);

            // Redefine Color for Enabled State
            vFinished.setBackgroundColor(Color.parseColor("#FFF6FAF2"));
            tvChapterNumber.setTextColor(mContext.getResources().getColor(R.color.course_background_dark_sample));
            tvChapterTitle.setTextColor(mContext.getResources().getColor(R.color.primary_text));
            tvChapterBrief.setTextColor(mContext.getResources().getColor(R.color.black_overlay));

            ivLock.setVisibility(View.GONE);
            tvLock.setVisibility(View.GONE);

            // Define Finished Percentage if it's not Lock
            vFinished.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, chapter.getFinishedPercentage()));

            vRemaining.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 100 - chapter.getFinishedPercentage()));

            if (chapter.getFinishedPercentage() > 0)
                tvFinishedPercentage.setText(String.valueOf(chapter.getFinishedPercentage()) + "% ဖတ္ၿပီး");
            else
                tvFinishedPercentage.setText("လံုးဝ မဖတ္ရေသး");
        }

        this.setupClickableViews(mSelfView, mController);
    }

    private void setupClickableViews(View selfView, final ControllerChapterItem controller) {
        selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onTapChapter(mChapterVO);
            }
        });
    }

    public interface ControllerChapterItem {
        void onTapChapter(ChapterVO chapter);
    }
}
