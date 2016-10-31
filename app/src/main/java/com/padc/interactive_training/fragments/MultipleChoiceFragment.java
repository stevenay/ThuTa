package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.QuestionVO;
import com.padc.interactive_training.data.vos.TodoListVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiceFragment extends Fragment {

    @BindView(R.id.tv_question_text)
    TextView tvQuestionText;

    @BindView(R.id.rdo_choice_one)
    RadioButton rdoChoiceOne;

    @BindView(R.id.rdo_choice_two)
    RadioButton rdoChoiceTwo;

    @BindView(R.id.rdo_choice_three)
    RadioButton rdoChoiceThree;

    private static final String BK_QUESTION_INDEX = "BK_QUESTION_INDEX";

    private int mQuestionIndex;
    private QuestionVO mQuestion;

    public static MultipleChoiceFragment newInstance(int questionIndex) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BK_QUESTION_INDEX, questionIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mQuestionIndex = bundle.getInt(BK_QUESTION_INDEX, 0);
            mQuestion = CourseModel.getInstance().getQuestionbyIndex(mQuestionIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        ButterKnife.bind(this, view);
        bindData();

        return view;
    }

    private void bindData() {
        if (mQuestion != null) {
            tvQuestionText.setText(mQuestion.getQuestionText());

            rdoChoiceOne.setText(mQuestion.getAnswers().get(0).getAnswerContent());
            rdoChoiceOne.setText(mQuestion.getAnswers().get(1).getAnswerContent());
            rdoChoiceOne.setText(mQuestion.getAnswers().get(2).getAnswerContent());
        }
    }
}
