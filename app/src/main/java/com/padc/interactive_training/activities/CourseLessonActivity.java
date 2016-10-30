package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.fragments.MultipleChoiceFragment;
import com.padc.interactive_training.fragments.PopupQuestionFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseLessonActivity extends AppCompatActivity {

    @BindView(R.id.rl_result_popup)
    RelativeLayout rlResultPopup;

    @BindView(R.id.tv_result_text)
    TextView tvResultText;

    private boolean resultCheck = false;

    public static Intent newIntent(){
        Intent newIntet = new Intent(InteractiveTrainingApp.getContext(), CourseLessonActivity.class);
        return newIntet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_lesson);
        ButterKnife.bind(this, this);



        if (savedInstanceState == null) {
            navigateToPopupQuestion();
        }
    }

    private void navigateToPopupQuestion() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, MultipleChoiceFragment.newInstance())
                .commit();
    }

    @OnClick(R.id.btn_check)
    public void onbtnCheckPressed(Button view) {
        rlResultPopup.setTranslationX(-1 * (rlResultPopup.getWidth() + 50));
        rlResultPopup.setVisibility(View.VISIBLE);

        if (resultCheck) {
            tvResultText.setText("မွန္တယ္။ သင့္အတြက္ ၂ မွတ္။");
            rlResultPopup.animate().translationX(0)
                    .setInterpolator(new DecelerateInterpolator());
            resultCheck = false;
            view.setText("Continue");
        } else {
            tvResultText.setText("လြဲေနတယ္။ ေျဖၾကည့္ပါဦး။");
            rlResultPopup.animate().translationX(0)
                    .setDuration(600)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            resultCheck = true;
        }

//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_fragment, R.anim.slide_out_fragment)
//                .replace(R.id.fl_container, PopupQuestionFragment.newInstance())
//                .commit();
    }


}
