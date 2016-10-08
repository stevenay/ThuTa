package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.data.vos.TodoListVO;
import com.padc.interactive_training.fragments.ChapterIntroFragment;
import com.padc.interactive_training.fragments.LessonCardFragment;
import com.padc.interactive_training.utils.ImageUtils;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.utils.ScreenUtils;
import com.padc.interactive_training.views.holders.LessonCardViewHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseFlowActivity extends AppCompatActivity
        implements LessonCardViewHolder.ControllerLessonCardItem,
        LoaderManager.LoaderCallbacks<Cursor>,
        LessonCardFragment.ControllerLessonCard,
        ChapterIntroFragment.ControllerChapterIntro {

    @BindView(R.id.pb_course_flow)
    ProgressBar pbCourseFlow;

    @BindView(R.id.btn_previous)
    Button btnPrevious;

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_todo_list)
    Button btnTodoList;

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    private int cardIndex = -1;
    private int totalCardNumber;
    private boolean chapterIntro = true;
    private boolean chapterIntroFromPrev;
    private String currentChapterId = "";
    private String mFirstChapterId = "";
    private int mLastAccessCardIndex = -1;
    private TodoListVO mAccessTodoList;

    private static final String IE_CHAPTER_ID = "IE_CHAPTER_ID";
    private static final String IE_COURSE_TITLE = "IE_COURSE_TITLE";
    private static final String IE_LAST_ACCESS_CARDINDEX = "IE_LAST_ACCESS_CARDINDEX";
    private String mChapterId;
    private String mCourseTitle;
    private static final int navigateToLessonCard = 1;
    protected static final int RC_TODO_LIST = 1236;

    RelativeLayout relativeLayout;

    public static Intent newIntent(String courseTitle, String chapterId, int lastAccessCardIndex) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseFlowActivity.class);
        intent.putExtra(IE_COURSE_TITLE, courseTitle);
        intent.putExtra(IE_CHAPTER_ID, chapterId);
        intent.putExtra(IE_LAST_ACCESS_CARDINDEX, lastAccessCardIndex);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_TODO_LIST) {
            this.onAccessTodoList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_flow);
        ButterKnife.bind(this, this);

        mChapterId = getIntent().getStringExtra(IE_CHAPTER_ID);
        mCourseTitle = getIntent().getStringExtra(IE_COURSE_TITLE);
        mLastAccessCardIndex = getIntent().getIntExtra(IE_LAST_ACCESS_CARDINDEX, -1);

        mFirstChapterId = CourseModel.getInstance().getChapterbyIndex(0).getChapterId();
        pbCourseFlow.setScaleY(1.5f);

        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.divider));
        relativeLayout.setLayoutParams(rlp);

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_FLOW_LOADER, null, this);
    }

    //region ButtonPressEvents
    @OnClick(R.id.btn_back)
    public void onbtnBackPressed(ImageButton view) {
        this.onBackPressed();
    }

    @OnClick(R.id.btn_share)
    public void onbtnSharePressed(ImageButton view) {
        this.getImage();
        File imagePath = new File(getApplicationContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, "com.padc.interactive_training.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    @OnClick(R.id.btn_next)
    public void onbtnNextPressed(Button view) {
        if ((cardIndex + 1) < totalCardNumber) {

            String cardId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex + 1).getCardId();
            TodoListVO todoList = CourseModel.getInstance().getTodoListbyCardId(cardId);
            if (todoList != null && !todoList.isFinishAccess()) {
                mAccessTodoList = todoList;
                btnNext.setVisibility(View.GONE);
                btnTodoList.setVisibility(View.VISIBLE);
            }

            String tempChapterId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex + 1).getChapterId();

            if (chapterIntro) {
                chapterIntro = false;
            } else if (!currentChapterId.isEmpty() && !this.currentChapterId.equals(tempChapterId)) {
                chapterIntro = true;
                currentChapterId = tempChapterId;
                CourseModel.getInstance().setChapterUnLock(currentChapterId);
                navigateToNewChapterIntro(currentChapterId, "next");
                return;
            }

            if (!chapterIntroFromPrev) {
                cardIndex++;
                CourseModel.getInstance().setLastAccessCardIndex(cardIndex);

                int cardCountinChapter = CourseModel.getInstance().getCardCountbyChapterId(currentChapterId);
                int firstCardIndex = CourseModel.getInstance().getFirstCardIndexbyChapterId(currentChapterId);

                int chapterFinished = (((cardIndex + 1) - firstCardIndex) * 100) / cardCountinChapter;
                CourseModel.getInstance().setChapterFinishPercentage(currentChapterId, chapterFinished);
                this.setProgressBar(cardIndex);
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
        if (btnTodoList.getVisibility() == View.VISIBLE) {
            btnTodoList.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        }

        if ((cardIndex - 1) >= 0) {
            String tempChapterId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex - 1).getChapterId();

            if (!chapterIntro) {
                if (!currentChapterId.isEmpty() && !this.currentChapterId.equals(tempChapterId)) {
                    chapterIntro = true;
                    chapterIntroFromPrev = true;
                    navigateToNewChapterIntro(currentChapterId, "prev");
                    return;
                }

                cardIndex--;
                this.setProgressBar(cardIndex);
            } else {
                chapterIntro = false;
            }

            if (chapterIntroFromPrev) {
                cardIndex--;
                this.setProgressBar(cardIndex);
                chapterIntroFromPrev = false;
            }

            currentChapterId = tempChapterId;
            navigateToLessonCard(cardIndex, "prev");
        } else {
            cardIndex = -1;
            this.setProgressBar(cardIndex);
            if (!chapterIntro) {
                navigateToNewChapterIntro(mFirstChapterId, "prev");
                chapterIntro = true;
            }
            Toast.makeText(getApplicationContext(), "သင္ခန္းစာမ်ား မရွိေတာ့ပါ", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_todo_list)
    public void onbtnTodoList(Button view) {
        navigateToTodoList();
    }
    //endregion

    //region NavigationMethods
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

    private void navigateToTodoList() {
        if (mAccessTodoList != null) {
            Intent intent = TodoListActivity.newIntent(mAccessTodoList.getTodoListId());
            startActivityForResult(intent, RC_TODO_LIST);
        }
    }
    //endregion

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
        final List<LessonCardVO> cardList = new ArrayList<>();
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
                    if (mLastAccessCardIndex > 0) {
                        cardIndex = mLastAccessCardIndex;
                        currentChapterId = CourseModel.getInstance().getLessonCardbyIndex(cardIndex).getChapterId();
                        chapterIntro = false;
                        navigateToLessonCard(mLastAccessCardIndex, "none");
                        setProgressBar(cardIndex);
                    } else if (mChapterId.isEmpty()) {
                        navigateToNewChapterIntro(mFirstChapterId, "next");
                        currentChapterId = mFirstChapterId;
                        setProgressBar(0);
                    } else {
                        navigateToNewChapterIntro(mChapterId, "next");
                        currentChapterId = mChapterId;
                        cardIndex = CourseModel.getInstance().getFirstCardIndexbyChapterId(mChapterId) - 1;
                        setProgressBar(cardIndex);
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

    //region HelperFunction
    private void setProgressBar(int currentIndex) {
        // has to plus one because it's based on Zero value.
        int overallFinish = (currentIndex + 1) * 100 / totalCardNumber;
        pbCourseFlow.setProgress(overallFinish);
    }

    private void getImage() {
        // save bitmap to cache directory
        try {
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time

            Bitmap bmap = ImageUtils.addWhiteBorderToBitmap(ScreenUtils.screenShot(this.flContainer), 20);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //region ControllerLessonCardFragment
    @Override
    public void onAccessTodoList() {
        if (mAccessTodoList != null) {
            mAccessTodoList.setFinishAccess(true);
            mAccessTodoList = null;
        }
        btnTodoList.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNextCard() {
        this.btnNext.performClick();
    }

    @Override
    public void onPreviousCard() {
        this.btnPrevious.performClick();
    }
    //endregion

    //region ControllerChapterIntroFragment
    @Override
    public void onNextChapter() {
        this.btnNext.performClick();
    }

    @Override
    public void onPreviousChapter() {
        this.btnPrevious.performClick();
    }
    //endregion
}
