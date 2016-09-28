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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.LessonCardAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.fragments.ChapterEndFragment;
import com.padc.interactive_training.fragments.ChapterIntroFragment;
import com.padc.interactive_training.fragments.LessonCardFragment;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.LessonCardViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseFlowActivity extends AppCompatActivity
        implements LessonCardViewHolder.ControllerLessonCardItem,
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.pb_course_flow)
    ProgressBar pbCourseFlow;

    @BindView(R.id.btn_previous)
    Button btnPrevious;

    @BindView(R.id.btn_next)
    Button btnNext;

    private int cardIndex = -1;
    private int totalCardNumber;
    private boolean chapterIntro = true;
    private boolean chapterIntroFromPrev;
    private String currentChapterId = "";
    private String mFirstChapterId = "";

    private static final String IE_CHAPTER_ID = "IE_CHAPTER_ID";
    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";
    private String mChapterId;
    private String mCourseTitle;
    private static final int navigateToLessonCard = 1;

    public static Intent newIntent(String courseTitle, String chapterId) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseFlowActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        intent.putExtra(IE_CHAPTER_ID, chapterId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_flow);
        ButterKnife.bind(this, this);

        mChapterId = getIntent().getStringExtra(IE_CHAPTER_ID);
        mCourseTitle = getIntent().getStringExtra(IE_COURSE_TITLE);
        mFirstChapterId = CourseModel.getInstance().getChapterbyIndex(0).getChapterId();

        pbCourseFlow.setScaleY(1.5f);

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_FLOW_LOADER, null, this);
    }

    //region ButtonPressEvents
    @OnClick(R.id.btn_back)
    public void onbtnBackPressed(ImageButton view) {
        this.onBackPressed();
    }

    @OnClick(R.id.btn_share)
    public void onbtnSharePressed(ImageButton view) {
        Intent intent = PopupQuestionActivity.newIntent("SampleLessonCardID");
        startActivity(intent);
    }

    @OnClick(R.id.btn_next)
    public void onbtnNextPressed(Button view) {
        if ((cardIndex+1) < totalCardNumber) {

            String tempChapterId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex+1).getChapterId();

            if (chapterIntro) {
                chapterIntro = false;
            } else if (!currentChapterId.isEmpty() && !this.currentChapterId.equals(tempChapterId)) {
                chapterIntro = true;
                currentChapterId = tempChapterId;
                navigateToNewChapterIntro(currentChapterId, "next");
                return;
            }

            if (!chapterIntroFromPrev) {
                cardIndex++;
            } else {
                chapterIntroFromPrev = false;
            }
            currentChapterId = tempChapterId;
            navigateToLessonCard(cardIndex, "next");
        } else {
            cardIndex = this.totalCardNumber - 1;
            Toast.makeText(getApplicationContext(), "သင္ခန္းစာမ်ား မရွိေတာ့ပါ", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_previous)
    public void onbtnPreviousPressed(Button view) {
        if ((cardIndex-1) >= 0) {
            String tempChapterId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex-1).getChapterId();

            if (!chapterIntro) {
                if (!currentChapterId.isEmpty() && !this.currentChapterId.equals(tempChapterId)) {
                    chapterIntro = true;
                    chapterIntroFromPrev = true;
                    navigateToNewChapterIntro(currentChapterId, "prev");
                    return;
                }

                cardIndex--;
            } else {
                chapterIntro = false;
            }

            if (chapterIntroFromPrev) {
                cardIndex--;
                chapterIntroFromPrev = false;
            }

            currentChapterId = tempChapterId;
            navigateToLessonCard(cardIndex, "prev");
        } else {
            cardIndex = -1;
            if (!chapterIntro) {
                navigateToNewChapterIntro(mFirstChapterId, "prev");
                chapterIntro = true;
            }
            Toast.makeText(getApplicationContext(), "သင္ခန္းစာမ်ား မရွိေတာ့ပါ", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private void navigateToLessonCard(int cardIndex, String direction) {

        if (direction == "next") {
            LessonCardFragment fragment = LessonCardFragment.newInstance(cardIndex, totalCardNumber);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        } else if (direction == "prev") {
            LessonCardFragment fragment = LessonCardFragment.newInstance(cardIndex, totalCardNumber);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_fragment, R.anim.slide_out_fragment)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        } else {
            LessonCardFragment fragment = LessonCardFragment.newInstance(cardIndex, totalCardNumber);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
        }

    }

    private void navigateToNewChapterIntro(String chapterId, String direction) {
        ChapterIntroFragment fragment = ChapterIntroFragment.newInstance(chapterId);

        if (direction == "next") {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_fragment, R.anim.slide_out_fragment)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        }
    }

    //region LoaderPattern
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.LessonCardEntry.buildLessonCardUriWithCourseTitle(mCourseTitle),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<LessonCardVO> cardList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                LessonCardVO lessonCard = LessonCardVO.parseFromCursor(data);
                cardList.add(lessonCard);
            } while (data.moveToNext());
        }

        CourseModel.getInstance().setCardListData(cardList);
        this.totalCardNumber = cardList.size();

        btnNext.setVisibility(View.VISIBLE);
        btnPrevious.setVisibility(View.VISIBLE);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == navigateToLessonCard) {
                    if (mChapterId.isEmpty())
                        navigateToNewChapterIntro(mFirstChapterId, "next");
                    else {
                        navigateToNewChapterIntro(mChapterId, "next");
                        cardIndex = CourseModel.getInstance().getFirstCardIndexbyChapterId(mChapterId) - 1;
                    }
                }
            }
        };
        handler.sendEmptyMessage(navigateToLessonCard);
        Log.d(InteractiveTrainingApp.TAG, "Retrieved cards DESC : " + cardList.size());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion

    //region ControllerLessonCardItem Implementation
    @Override
    public void onTapPinButton(LessonCardVO lessonCard) {
        if (lessonCard.isBookmarked()) {
            lessonCard.setBookmarked(false);
        } else {
            lessonCard.setBookmarked(true);
        }
    }

    @Override
    public void onTapRequestButton() {
        Intent intent = TodoListActivity.newIntent("Sample CourseID");
        startActivity(intent);
    }
    //endregion
}
