package com.padc.interactive_training.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.activities.TodoListActivity;
import com.padc.interactive_training.adapters.TodoAdapter;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.data.vos.TodoListVO;
import com.padc.interactive_training.views.holders.ChapterViewHolder;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonCardFragment extends Fragment {

    @BindView(R.id.btn_pin)
    ImageButton btnPin;

    @BindView(R.id.btn_todo_lists)
    Button btnTodoLists;

    @BindView(R.id.iv_lesson_image)
    ImageView ivLessonImage;

    @BindView(R.id.tv_lesson_description)
    TextView tvLessonDesp;

    @BindView(R.id.tv_page_number_top)
    TextView tvPageNumberTop;

    @BindView(R.id.layout_container)
    NestedScrollView cardLesson;

    private LessonCardVO mLessonCard;
    private ControllerLessonCard mController;
    protected static final int RC_TODO_LIST = 1237;

    private static final String BK_CARD_INDEX = "BK_CARD_INDEX";
    private static final String BK_CARD_COUNT = "BK_CARD_COUNT";
    private int mCardIndex;
    private int mTotalCardinChapter;
    private TodoListVO mCurrentTodoList;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    public static LessonCardFragment newInstance(int cardIndex, int totalCardinChapter) {
        LessonCardFragment fragment = new LessonCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BK_CARD_INDEX, cardIndex);
        bundle.putInt(BK_CARD_COUNT, totalCardinChapter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = (ControllerLessonCard) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == RC_TODO_LIST ) {
            mController.onAccessTodoList();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCardIndex = bundle.getInt(BK_CARD_INDEX, 0);
            mTotalCardinChapter = bundle.getInt(BK_CARD_COUNT, 0);
            mLessonCard = CourseModel.getInstance().getLessonCardbyIndex(mCardIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_card, container, false);
        ButterKnife.bind(this, view);
        bindData();

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

//                    @Override
//                    public boolean onDown(MotionEvent e) {
//                        return true;
//                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mController.onNextCard();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mController.onPreviousCard();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        cardLesson.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        tvLessonDesp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        return view;
    }

    private void bindData()
    {
        if (!mLessonCard.getLessonImageUrl().toString().isEmpty()) {
            ivLessonImage.setVisibility(View.VISIBLE);
            Glide.with(ivLessonImage.getContext())
                    .load(mLessonCard.getLessonImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivLessonImage);
        } else
            ivLessonImage.setVisibility(View.GONE);

        tvLessonDesp.setText(this.mLessonCard.getLessonDescription());
        tvPageNumberTop.setText(String.valueOf(mCardIndex + 1) + " of " + String.valueOf(mTotalCardinChapter) + " pages");

        if (mLessonCard.isBookmarked())
            btnPin.setImageResource(R.drawable.ic_pin_green_48);
        else
            btnPin.setImageResource(R.drawable.ic_pin_48);

        TodoListVO todoList = CourseModel.getInstance().getTodoListbyCardId(mLessonCard.getCardId());
        if (todoList != null) {
            mCurrentTodoList = todoList;
            btnTodoLists.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_pin)
    public void onbtnPinPressed(View button) {
        if (mLessonCard.isBookmarked()) {
            mLessonCard.setBookmarked(false);
            btnPin.setImageResource(R.drawable.ic_pin_48);
        } else {
            mLessonCard.setBookmarked(true);
            btnPin.setImageResource(R.drawable.ic_pin_green_48);
        }
    }

    @OnClick(R.id.btn_todo_lists)
    public void onbtnTodoLists(View button) {
        if (mCurrentTodoList != null) {
            Intent intent = TodoListActivity.newIntent(mCurrentTodoList.getTodoListId());
            startActivityForResult(intent, RC_TODO_LIST);
        }
    }

    public interface ControllerLessonCard {
        void onAccessTodoList();
        void onNextCard();
        void onPreviousCard();
    }
}
