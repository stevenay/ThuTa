package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.QuestionVO;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonResultFragment extends Fragment {

    private static final String BK_LESSON_INDEX = "BK_LESSON_INDEX";

    private int mQuestionIndex;
    private QuestionVO mQuestion;

    public static LessonResultFragment newInstance(String lessonIndex) {
        LessonResultFragment fragment = new LessonResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_LESSON_INDEX, lessonIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_result, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_finish)
    public void onbtnFinish(Button view) {
        this.getActivity().onBackPressed();
    }
}
