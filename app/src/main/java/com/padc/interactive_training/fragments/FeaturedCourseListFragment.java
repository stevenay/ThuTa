package com.padc.interactive_training.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.FeaturedCourseAdapter;
import com.padc.interactive_training.animators.RecyclerItemAnimator;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.views.holders.FeaturedCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturedCourseListFragment extends Fragment {

    @BindView(R.id.rv_lifestyle_list)
    RecyclerView rvLifestyleList;

    @BindView(R.id.rv_technology_list)
    RecyclerView rvTechnologyList;

    @BindView(R.id.rv_cooking_list)
    RecyclerView rvCookingList;

    private FeaturedCourseAdapter featuredCourseAdapter;
    private FeaturedCourseViewHolder.ControllerFeaturedCourseItem controllerCourseItem;

    public static FeaturedCourseListFragment newInstance()
    {
        FeaturedCourseListFragment fragment = new FeaturedCourseListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_featured_course_list, container, false);
        ButterKnife.bind(this, view);

        setupFeaturedCourse();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerCourseItem = (FeaturedCourseViewHolder.ControllerFeaturedCourseItem) context;
    }

    private void setupFeaturedCourse() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rvLifestyleList.setLayoutManager(linearLayoutManager);

        featuredCourseAdapter = new FeaturedCourseAdapter(prepareSampleCourseList(), controllerCourseItem);
        rvLifestyleList.setAdapter(featuredCourseAdapter);
        rvLifestyleList.setItemAnimator(new RecyclerItemAnimator());

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rvTechnologyList.setLayoutManager(linearLayoutManager1);
        rvTechnologyList.setAdapter(featuredCourseAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rvCookingList.setLayoutManager(linearLayoutManager2);
        rvCookingList.setAdapter(featuredCourseAdapter);
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
