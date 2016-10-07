package com.padc.interactive_training.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.activities.RegisteredCourseDetailActivity;
import com.padc.interactive_training.adapters.FeaturedCourseAdapter;
import com.padc.interactive_training.animators.RecyclerItemAnimator;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.AuthorVO;
import com.padc.interactive_training.data.vos.CourseCategoryVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.utils.TransitionHelper;
import com.padc.interactive_training.views.holders.FeaturedCourseViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeaturedCourseListFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.card_view)
    CardView cardFeaturedCourse;

    @BindView(R.id.card_view_technology)
    CardView cardTechnology;

    @BindView(R.id.card_view_cooking)
    CardView cardCooking;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.tv_course_title)
    TextView tvCourseTitle;

    @BindView(R.id.tv_duration_author)
    TextView tvDurationAuthor;

    @BindView(R.id.tv_likes_count)
    TextView tvLikesCount;

    @BindView(R.id.iv_course_cover_image)
    ImageView ivCourseCoverImage;

    @BindView(R.id.rv_lifestyle_list)
    RecyclerView rvLifestyleList;

    @BindView(R.id.rv_technology_list)
    RecyclerView rvTechnologyList;

    @BindView(R.id.rv_cooking_list)
    RecyclerView rvCookingList;

    private FeaturedCourseAdapter featuredCourseAdapter;
    private FeaturedCourseViewHolder.ControllerFeaturedCourseItem controllerCourseItem;

    public static FeaturedCourseListFragment newInstance() {
        FeaturedCourseListFragment fragment = new FeaturedCourseListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_featured_course_list, container, false);
        ButterKnife.bind(this, view);

        List<CourseVO> featuredCourses = CourseModel.getInstance().getCourseList();
        if (featuredCourses != null && featuredCourses.size() > 0)
            bindFeaturedCourseData(featuredCourses.get(0));

        setupFeaturedCourse();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(InteractiveTrainingConstants.COURSE_LIST_LOADER, null, this);
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

        cardTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), R.string.ContentInDev, Toast.LENGTH_LONG).show();
            }
        });

        cardCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), R.string.ContentInDev, Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<CourseVO> prepareSampleCourseList() {
        List<CourseVO> courseList = new ArrayList<>();

        CourseVO courseOne = new CourseVO();
        courseOne.setTitle("UV ေရာင္ျခည္ကို ဘယ္လိုကာကြယ္မလဲ");

        CourseCategoryVO category = new CourseCategoryVO();
        category.setCategoryName("LifeStyle");
        courseOne.setCourseCategory(category);

        courseOne.setDurationInMinute(15);

        AuthorVO author = new AuthorVO();
        author.setAuthorName("Admin Team");

        courseOne.setAuthor(author);
        courseOne.setColorCode("#aed582");
        courseOne.setCoverPhotoUrl("co_terrace.png");
        courseList.add(courseOne);

        CourseVO courseTwo = new CourseVO();
        courseTwo.setTitle("အားကစားကို နည္းမွန္လမ္းမွန္ ျပဳလုပ္နည္းမ်ား");

        CourseCategoryVO category1 = new CourseCategoryVO();
        category1.setCategoryName("Sports and Fitness");

        courseTwo.setCourseCategory(category1);
        courseTwo.setDurationInMinute(15);
        courseTwo.setAuthor(author);
        courseTwo.setColorCode("#81c683");
        courseOne.setCoverPhotoUrl("co_runner.png");
        courseList.add(courseTwo);

        CourseVO courseThree = new CourseVO();
        courseThree.setTitle("C# အသံုးျပဳ Console Application တည္ေဆာက္နည္း");

        CourseCategoryVO category2 = new CourseCategoryVO();
        category2.setCategoryName("Programming");

        courseThree.setCourseCategory(category2);
        courseThree.setDurationInMinute(10);
        courseThree.setAuthor(author);
        courseThree.setColorCode("#25c6da");
        courseOne.setCoverPhotoUrl("co_terrace.png");
        courseList.add(courseThree);

        return courseList;
    }

    private void bindFeaturedCourseData(CourseVO featuredCourse) {
        Log.d("BindFeaturedCourseData", "do the method");
        tvCategoryName.setText(featuredCourse.getCategoryName());
        tvCourseTitle.setText(featuredCourse.getTitle());
        tvDurationAuthor.setText(String.valueOf(featuredCourse.getDurationInMinute()) + " min - " + featuredCourse.getAuthorName());

        Glide.with(ivCourseCoverImage.getContext())
                .load(featuredCourse.getCoverPhotoUrl())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivCourseCoverImage);

        tvLikesCount.setText(String.valueOf(featuredCourse.getLikesCount()) + " likes");

        setupClickableViews(featuredCourse.getTitle());
    }

    private void setupClickableViews(final String courseTitle) {
        this.ivCourseCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCourseDetail(courseTitle);
            }
        });

        cardFeaturedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCourseDetail(courseTitle);
            }
        });
    }

    private void navigateToCourseDetail(String courseTitle)
    {
        Intent intent = RegisteredCourseDetailActivity.newIntent(courseTitle);

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this.getActivity(), true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this.getActivity(), pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(InteractiveTrainingApp.TAG, "course Loaded");
        return new CursorLoader(getContext(),
                CoursesContract.CourseEntry.CONTENT_URI,
                null, // projection - {"name", "location"}
                null, // selection - "region = ? AND popular = ?"
                null, // selectionArgs - {"upper_myanmar", "very_high"}
                CoursesContract.CourseEntry.COLUMN_TITLE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CourseVO featuredCourse = null;
        if (data != null && data.moveToFirst()) {
            featuredCourse = CourseVO.parseFromCursor(data);
            this.bindFeaturedCourseData(featuredCourse);
            CourseModel.getInstance().setStoredFeaturedCourseData(featuredCourse);
        }

        Log.d(InteractiveTrainingApp.TAG, "Retrieved featured course : " + featuredCourse);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}