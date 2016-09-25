package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.LessonCardVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonCardFragment extends Fragment {

    @BindView(R.id.btn_pin)
    ImageButton btnPin;

    private LessonCardVO mLessonCard;

    public static LessonCardFragment newInstance(int cardIndex) {
        LessonCardFragment fragment = new LessonCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLessonCard = new LessonCardVO();
        mLessonCard.setBookmarked(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_card, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_pin)
    public void onbtnPinPressed(View button) {
        if (mLessonCard.isBookmarked()) {
            mLessonCard.setBookmarked(false);
            ((ImageButton) (button)).setImageResource(R.drawable.ic_pin_48);
        } else {
            mLessonCard.setBookmarked(true);
            ((ImageButton) (button)).setImageResource(R.drawable.ic_pin_green_48);
        }
    }
}
