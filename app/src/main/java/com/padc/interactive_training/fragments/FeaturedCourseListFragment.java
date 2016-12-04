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
import com.padc.interactive_training.activities.CourseOverviewActivity;
import com.padc.interactive_training.activities.RegisteredCourseDetailActivity;
import com.padc.interactive_training.adapters.ArticleAdapter;
import com.padc.interactive_training.adapters.FeaturedCourseAdapter;
import com.padc.interactive_training.animators.RecyclerItemAnimator;
import com.padc.interactive_training.data.models.CourseModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.AuthorVO;
import com.padc.interactive_training.data.vos.CourseCategoryVO;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.utils.RecyclerViewUtils;
import com.padc.interactive_training.utils.TransitionHelper;
import com.padc.interactive_training.views.holders.FeaturedCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeaturedCourseListFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_featured_courses)
    RecyclerView rvFeaturedCourse;

    @BindView(R.id.card_view)
    CardView cardFeaturedCourse;

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

        setLayoutManagerOfCourseRecyclerView();
        setAdapterToCourseRecyclerView();

        List<CourseVO> featuredCourses = CourseModel.getInstance().getCourseList();
        if (featuredCourses != null && featuredCourses.size() > 0)
            bindFeaturedCourseData(featuredCourses.get(0));

        setupFeaturedCourse();

        return view;
    }

    private void setLayoutManagerOfCourseRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state){
                return 300;
            }
        };
        rvFeaturedCourse.setLayoutManager(linearLayoutManager);
    }

    private void setAdapterToCourseRecyclerView() {
        featuredCourseAdapter = new FeaturedCourseAdapter(controllerCourseItem);
        rvFeaturedCourse.setAdapter(featuredCourseAdapter);
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
        Intent intent;
        if (CourseModel.getInstance().getStoredFeaturedCourseData() != null
                && CourseModel.getInstance().getStoredFeaturedCourseData().isRegistered()) {
            intent = RegisteredCourseDetailActivity.newIntent(courseTitle);
        } else {
            intent = CourseOverviewActivity.newIntent("SampleCourseName");
        }

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