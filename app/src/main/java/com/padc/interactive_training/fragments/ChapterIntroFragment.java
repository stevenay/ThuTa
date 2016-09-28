package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.ChapterVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterIntroFragment extends Fragment {

    @BindView(R.id.tv_chapter_number)
    TextView tvChapterNumber;

    @BindView(R.id.tv_chapter_title)
    TextView tvChapterTitle;

    @BindView(R.id.tv_chapter_brief)
    TextView tvChapterBrief;

    @BindView(R.id.tv_duration)
    TextView tvDuration;

    @BindView(R.id.tv_card_count)
    TextView tvCardCount;

    private static final String BK_CHAPTER_ID = "BK_CHAPTER_ID";

    private String mChapterId = "";
    private ChapterVO mChapter;

    public static ChapterIntroFragment newInstance(String chapterId)
    {
        ChapterIntroFragment fragment = new ChapterIntroFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_CHAPTER_ID, chapterId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mChapterId = bundle.getString(BK_CHAPTER_ID, "");
            mChapter = CourseModel.getInstance().getChapterbyId(mChapterId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_intro, container, false);
        ButterKnife.bind(this, view);
        bindData();

        return view;
    }

    private void bindData()
    {
        tvChapterNumber.setText("chapter " + String.valueOf(mChapter.getChapterNumber()));

        String str = mChapter.getTitle();
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }

        tvChapterTitle.setText(builder.toString());
        tvChapterBrief.setText(mChapter.getChapterBrief());

        String durtionInMinute = tvDuration.getResources().getQuantityString(
                R.plurals.minutes_count, mChapter.getDurationInMins(), mChapter.getDurationInMins()
        );
        tvDuration.setText(durtionInMinute);

        String cardsCount = getContext().getResources().getQuantityString(
                R.plurals.cards_count, mChapter.getLessonCount(), mChapter.getLessonCount()
        );
        tvCardCount.setText(cardsCount);

    }


}
