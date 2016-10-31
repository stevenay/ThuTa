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
import android.util.Log;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.CourseLessonVO;
import com.padc.interactive_training.data.vos.QuestionVO;
import com.padc.interactive_training.fragments.LessonResultFragment;
import com.padc.interactive_training.fragments.MultipleChoiceFragment;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import butterknife.ButterKnife;

public class CourseLessonActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>,
    MultipleChoiceFragment.ControllerQuestion {

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

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_FLOW_LOADER, null, this);
    }

    private void navigateToPopupQuestion(int questionIndex) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, MultipleChoiceFragment.newInstance(questionIndex))
                .commit();
    }

    private void navigateToLessonResult(String lessonId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, LessonResultFragment.newInstance(lessonId))
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
        if (data != null && data.moveToFirst()) {
            mCourseLesson = CourseLessonVO.parseFromCursor(data);
            mCourseLesson.setQuestions(QuestionVO.loadQuestionsByLessonId(mCourseLesson.getLessonId()));

            totalQuestionNumber = mCourseLesson.getQuestions().size();
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

    //region Controller Question

    @Override
    public void onCheckAnswer(int questionIndex) {
        this.questionIndex = questionIndex;
        if (this.questionIndex < totalQuestionNumber - 1) {
            questionIndex++;
            navigateToPopupQuestion(questionIndex);
        } else {
            navigateToLessonResult(mCourseLesson.getLessonId());
        }
    }

    //endregion

}
