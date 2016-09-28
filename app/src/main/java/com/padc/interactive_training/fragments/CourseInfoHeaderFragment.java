package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseInfoHeaderFragment extends Fragment {

    @BindView(R.id.iv_course_cover_image)
    ImageView ivCoverImage;

    @BindView(R.id.tv_course_title)
    TextView tvCourseTitle;

    private static final String BK_COURSE_TITLE = "BK_COURSE_TITLE";
    private static final String BK_COVER_PHOTO_URL = "BK_COVER_PHOTO_URL";

    public static CourseInfoHeaderFragment newInstance(String courseTitle, String coverPhotoUrl) {
        CourseInfoHeaderFragment fragment = new CourseInfoHeaderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_COURSE_TITLE, courseTitle);
        bundle.putString(BK_COVER_PHOTO_URL, coverPhotoUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CourseInfoHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_info_header, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        String courseTitle = bundle.getString(BK_COURSE_TITLE, "Course title");
        String coverPhotoUrl = bundle.getString(BK_COVER_PHOTO_URL, "Cover Photo");

        tvCourseTitle.setText(courseTitle);

        Glide.with(ivCoverImage.getContext())
                .load(coverPhotoUrl)
                .asBitmap()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivCoverImage);

        return view;
    }

}
