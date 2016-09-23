package com.padc.interactive_training.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.MyCourseAdapter;
import com.padc.interactive_training.animators.RecyclerItemAnimator;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.views.holders.MyCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCourseListFragment extends Fragment {

    @BindView(R.id.rvMyCourse)
    RecyclerView rvMyCourse;

    private MyCourseAdapter myCourseAdapter;
    private MyCourseViewHolder.ControllerCourseItem controllerCourseItem;

    public static MyCourseListFragment newInstance() {
        MyCourseListFragment fragment = new MyCourseListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_course_list, container, false);
        ButterKnife.bind(this, view);

        setupMyCourse();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerCourseItem = (MyCourseViewHolder.ControllerCourseItem) context;
    }

    private void setupMyCourse() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvMyCourse.setLayoutManager(linearLayoutManager);

        myCourseAdapter = new MyCourseAdapter(prepareSampleCourseList(), controllerCourseItem);
        rvMyCourse.setAdapter(myCourseAdapter);
        rvMyCourse.setItemAnimator(new RecyclerItemAnimator());
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvMyCourse.smoothScrollToPosition(0);
                myCourseAdapter.showLoadingView();
            }
        }, 500);
    }

    public List<CourseVO> prepareSampleCourseList() {
        List<CourseVO> courseList = new ArrayList<>();

        CourseVO courseOne = new CourseVO();
        courseOne.setTitle("UV ေရာင္ျခည္ကို ဘယ္လိုကာကြယ္မလဲ");
        courseOne.setCategoryName("LifeStyle");
        courseOne.setDurationInMinute(15);
        courseOne.setAuthorName("Admin Team");
        courseOne.setColorCode("#aed582");
        courseOne.setCoverPhotoUrl("co_terrace.png");
        courseList.add(courseOne);

        CourseVO courseTwo = new CourseVO();
        courseTwo.setTitle("အားကစားကို နည္းမွန္လမ္းမွန္ ျပဳလုပ္နည္းမ်ား");
        courseTwo.setCategoryName("Sports and Fitness");
        courseTwo.setDurationInMinute(15);
        courseTwo.setAuthorName("Admin Team");
        courseTwo.setColorCode("#81c683");
        courseOne.setCoverPhotoUrl("co_runner.png");
        courseList.add(courseTwo);

        CourseVO courseThree = new CourseVO();
        courseThree.setTitle("C# အသံုးျပဳ Console Application တစ္ခု ဘယ္လိုတည္ေဆာက္မလဲ");
        courseThree.setCategoryName("Programming");
        courseThree.setDurationInMinute(10);
        courseThree.setAuthorName("Admin Team");
        courseThree.setColorCode("#25c6da");
        courseOne.setCoverPhotoUrl("co_terrace.png");
        courseList.add(courseThree);

        return courseList;
    }
}
