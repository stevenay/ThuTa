package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.LessonCardAdapter;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.fragments.ChapterEndFragment;
import com.padc.interactive_training.fragments.LessonCardFragment;
import com.padc.interactive_training.views.holders.LessonCardViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseFlowActivity extends AppCompatActivity
    implements LessonCardViewHolder.ControllerLessonCardItem {

    @BindView(R.id.pb_course_flow)
    ProgressBar pbCourseFlow;

    @BindView(R.id.btn_previous)
    Button btnPrevious;

    @BindView(R.id.btn_next)
    Button btnNext;

    private int cardIndex = -1;
    private int totalCardNumber = 10;
    private LessonCardAdapter arrayAdapter;

    private static final String IE_COURSE_ID = "IE_COURSE_ID";

    public static Intent newIntent(String courseID) {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), CourseFlowActivity.class);
        intent.putExtra(IE_COURSE_ID, courseID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_flow);
        ButterKnife.bind(this, this);

        pbCourseFlow.setScaleY(1.5f);

        // arrayAdapter = new LessonCardAdapter(this, getDummyData(), this);

    }

    private ArrayList<LessonCardVO> getDummyData() {
        ArrayList<LessonCardVO> al = new ArrayList<>();

        LessonCardVO card = new LessonCardVO();
        card.setLessonDescription("John");
        card.setCardOrderNumber(1);
        al.add(card);

        LessonCardVO card1 = new LessonCardVO();
        card1.setLessonDescription("John");
        card1.setCardOrderNumber(2);
        al.add(card1);

        LessonCardVO card2 = new LessonCardVO();
        card2.setLessonDescription("John");
        card2.setCardOrderNumber(3);
        al.add(card2);

        return al;
    }

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
        cardIndex++;
        if (cardIndex < totalCardNumber) {
            navigateToLessonCard(cardIndex, "next");
        } else {
            cardIndex = this.totalCardNumber - 1;
            Toast.makeText(getApplicationContext(), "သင္ခန္းစာမ်ား မရွိေတာ့ပါ", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_previous)
    public void onbtnPreviousPressed(Button view) {
        cardIndex--;
        if (cardIndex >= 0) {
            navigateToLessonCard(cardIndex, "previous");
        } else {
            cardIndex = 0;
            Toast.makeText(getApplicationContext(), "သင္ခန္းစာမ်ား မရွိေတာ့ပါ", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLessonCard(int cardIndex, String direction) {

        if (direction == "next")
        {
            LessonCardFragment fragment = LessonCardFragment.newInstance(cardIndex);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        } else {
            LessonCardFragment fragment = LessonCardFragment.newInstance(cardIndex);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_fragment, R.anim.slide_out_fragment)
                    .replace(R.id.fl_container, fragment)
                    .commit();
        }

    }

    private void navigateToChapterEnd() {
        ChapterEndFragment fragment = ChapterEndFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                .replace(R.id.fl_container, fragment)
                .commit();
    }

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
