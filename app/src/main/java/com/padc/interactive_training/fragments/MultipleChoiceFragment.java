package com.padc.interactive_training.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.QuestionVO;
import com.padc.interactive_training.data.vos.TodoListVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.rdo_group)
    RadioGroup rdgChoice;

    @BindView(R.id.rl_result_popup)
    RelativeLayout rlResultPopup;

    @BindView(R.id.tv_result_text)
    TextView tvResultText;

    private static final String BK_QUESTION_INDEX = "BK_QUESTION_INDEX";

    private int mQuestionIndex;
    private QuestionVO mQuestion;

    private ControllerQuestion controllerQuestion;

    public static MultipleChoiceFragment newInstance(int questionIndex) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BK_QUESTION_INDEX, questionIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerQuestion = (ControllerQuestion) context;
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
            rdoChoiceTwo.setText(mQuestion.getAnswers().get(1).getAnswerContent());
            rdoChoiceThree.setText(mQuestion.getAnswers().get(2).getAnswerContent());
        }
    }

    @OnClick(R.id.btn_check)
    public void onbtnCheckPressed(Button view) {
        if (view.getText().equals("Continue")) {
            rlResultPopup.setVisibility(View.INVISIBLE);
            view.setText("Check");
            controllerQuestion.onCheckAnswer(mQuestionIndex);
            return;
        }

        rlResultPopup.setTranslationX(-1 * (rlResultPopup.getWidth() + 50));
        rlResultPopup.setVisibility(View.VISIBLE);

        if (rdgChoice.getCheckedRadioButtonId() != -1) {
            int id = rdgChoice.getCheckedRadioButtonId();

            View radioButton = rdgChoice.findViewById(id);
            int radioId = rdgChoice.indexOfChild(radioButton);

            if (mQuestion.getAnswers().get(radioId).getIsAnswer()) {
                tvResultText.setText("မွန္တယ္။");
                rlResultPopup.animate().translationX(0)
                        .setInterpolator(new DecelerateInterpolator());
                view.setText("Continue");
            } else {
                tvResultText.setText("လြဲေနတယ္။ ေျဖၾကည့္ပါဦး။");
                rlResultPopup.animate().translationX(0)
                    .setDuration(600)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            }
        }
    }

    public interface ControllerQuestion {
        void onCheckAnswer(int questionIndex);
    }

}
