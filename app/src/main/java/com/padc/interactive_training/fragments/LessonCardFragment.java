package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.data.vos.LessonCardVO;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonCardFragment extends Fragment {

    @BindView(R.id.btn_pin)
    ImageButton btnPin;

    @BindView(R.id.iv_lesson_image)
    ImageView ivLessonImage;

    @BindView(R.id.tv_lesson_description)
    TextView tvLessonDesp;

    @BindView(R.id.tv_page_number_top)
    TextView tvPageNumberTop;

    private LessonCardVO mLessonCard;

    private static final String BK_CARD_INDEX = "BK_CARD_INDEX";
    private static final String BK_CARD_COUNT = "BK_CARD_COUNT";
    private int mCardIndex;
    private int mTotalCardinChapter;

    public static LessonCardFragment newInstance(int cardIndex, int totalCardinChapter) {
        LessonCardFragment fragment = new LessonCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BK_CARD_INDEX, cardIndex);
        bundle.putInt(BK_CARD_COUNT, totalCardinChapter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCardIndex = bundle.getInt(BK_CARD_INDEX, 0);
            mTotalCardinChapter = bundle.getInt(BK_CARD_COUNT, 0);
            mLessonCard = CourseModel.getInstance().getLessonCardbyIndex(mCardIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_card, container, false);
        ButterKnife.bind(this, view);
        bindData();

        return view;
    }

    private void bindData()
    {
        if (!mLessonCard.getLessonImageUrl().toString().isEmpty()) {
            ivLessonImage.setVisibility(View.VISIBLE);
            Glide.with(ivLessonImage.getContext())
                    .load(mLessonCard.getLessonImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivLessonImage);
        } else
            ivLessonImage.setVisibility(View.GONE);

        tvLessonDesp.setText(this.mLessonCard.getLessonDescription());
        tvPageNumberTop.setText(String.valueOf(mCardIndex + 1) + " of " + String.valueOf(mTotalCardinChapter) + " pages");

        if (mLessonCard.isBookmarked())
            btnPin.setImageResource(R.drawable.ic_pin_green_48);
        else
            btnPin.setImageResource(R.drawable.ic_pin_48);
    }

    @OnClick(R.id.btn_pin)
    public void onbtnPinPressed(View button) {
        if (mLessonCard.isBookmarked()) {
            mLessonCard.setBookmarked(false);
            btnPin.setImageResource(R.drawable.ic_pin_48);
        } else {
            mLessonCard.setBookmarked(true);
            btnPin.setImageResource(R.drawable.ic_pin_green_48);
        }
    }
}
