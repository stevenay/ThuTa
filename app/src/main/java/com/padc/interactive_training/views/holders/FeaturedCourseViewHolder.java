package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.CourseVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 9/23/2016.
 */
public class FeaturedCourseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_course_cover_image)
    ImageView ivCourseCoverImage;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.tv_course_title)
    TextView tvCourseTitle;

    @BindView(R.id.tv_duration)
    TextView tvDuration;

//    @BindView(R.id.vImageRoot)
//    public FrameLayout vImageRoot;

    @BindView(R.id.btnComments)
    ImageButton btnComments;

    @BindView(R.id.btnLike)
    public ImageButton btnLike;

    @BindView(R.id.btnMore)
    ImageButton btnMore;

    @BindView(R.id.vBgLike)
    public View vBgLike;

    @BindView(R.id.ivLike)
    public ImageView ivLike;

    @BindView(R.id.tsLikesCounter)
    public TextSwitcher tsLikesCounter;

    private ControllerFeaturedCourseItem mController;
    private CourseVO mCourseVO;
    private View mSelfView;
    private Context mContext;

    public FeaturedCourseViewHolder(View view, ControllerFeaturedCourseItem controller) {
        super(view);
        ButterKnife.bind(this, view);

        this.mController = controller;
        this.mSelfView = view;
        this.mContext = InteractiveTrainingApp.getContext();
    }

    private void setupClickableViews(View selfView, final ControllerFeaturedCourseItem controller) {
        this.ivCourseCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                controller.onFeaturedCoverImageClick();
                Toast.makeText(mContext, R.string.ContentInDev, Toast.LENGTH_LONG).show();

            }
        });

        selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                controller.onTapFeaturedCourse(mCourseVO);
                Toast.makeText(mContext, R.string.ContentInDev, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bindData(CourseVO courseVO) {
        mCourseVO = courseVO;
        ivCourseCoverImage.setBackgroundColor(Color.parseColor(mCourseVO.getColorCode()));
        tvCourseTitle.setText(mCourseVO.getTitle());
        tvCategoryName.setText(mCourseVO.getCourseCategory().getCategoryName());
        // tvCategoryName.setTextColor(Color.parseColor(mCourseVO.getColorCode()));
        String durationAndAuthor = mCourseVO.getDurationInMinute().toString() + " mins - Admin Team";
        tvDuration.setText(durationAndAuthor);

//        Glide.with(ivCourseCoverImage.getContext())
//                .load(R.drawable.uv_64)
//                .asBitmap().centerCrop()
//                .placeholder(R.drawable.misc_09_256)
//                .error(R.drawable.misc_09_256)
//                .into(ivCourseCoverImage);

        setupClickableViews(mSelfView, mController);
    }

    public interface ControllerFeaturedCourseItem {
        void onTapFeaturedCourse(CourseVO course);

        void onFeaturedCoverImageClick();
    }
}
