package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.CourseLessonVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.data.vos.QuestionVO;
import com.padc.interactive_training.fragments.MultipleChoiceFragment;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseLessonActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rl_result_popup)
    RelativeLayout rlResultPopup;

    @BindView(R.id.tv_result_text)
    TextView tvResultText;

    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";
    private static final String IE_CHAPTER_ID = "IE_CHAPTER_ID";

    private String mChapterId;
    private String mCourseTitle;
    private int questionIndex;
    private int totalQuestionNumber;
    private boolean resultCheck = false;
    private CourseLessonVO mCourseLesson;

    private static final int msgNavigateToPopupQuestion = 1;

    public static Intent newIntent(String courseTitle, String chapterId)
    {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseLessonActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        intent.putExtra(IE_CHAPTER_ID, chapterId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_lesson);
        ButterKnife.bind(this, this);

        mChapterId = getIntent().getStringExtra(IE_CHAPTER_ID);
        mCourseTitle = getIntent().getStringExtra(IE_COURSE_TITLE);

//        if (savedInstanceState == null) {
//            navigateToPopupQuestion();
//        }

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_FLOW_LOADER, null, this);
    }

    private void navigateToPopupQuestion(int questionIndex) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, MultipleChoiceFragment.newInstance(questionIndex))
                .commit();
    }

    //region LoaderPattern
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.CourseLessonEntry.buildCourseLessonUriWithCourseTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final List<QuestionVO> questionList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            mCourseLesson = CourseLessonVO.parseFromCursor(data);
            mCourseLesson.setQuestions(CourseLessonVO.loadQuestionsByLessonId(mCourseLesson.getLessonId()));

            CourseModel.getInstance().setQuestionListData(mCourseLesson.getQuestions());

            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == msgNavigateToPopupQuestion) {
                        navigateToPopupQuestion(questionIndex);
                    }
                }
            };
            handler.sendEmptyMessage(msgNavigateToPopupQuestion);
        }

        this.totalQuestionNumber = mCourseLesson.getQuestions().size();
        Log.d(InteractiveTrainingApp.TAG, "Retrieved Questions DESC : " + mCourseLesson.getQuestions().size());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion

    //region BindData
    private void bindData(CourseLessonVO mCourseLesson) {

        navigateToPopupQuestion(questionIndex);

    }
    //endregion

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
