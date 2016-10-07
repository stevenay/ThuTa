package com.padc.interactive_training.views.holders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.components.SendingProgressView;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.views.items.LoadingCourseItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCourseViewHolder extends RecyclerView.ViewHolder {
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

    @BindView(R.id.layout_image_cover)
    public LinearLayout layoutImageCover;

    private ControllerCourseItem mController;
    private CourseVO mCourseVO;
    private View mSelfView;
    private Context mContext;

    public SendingProgressView vSendingProgress;
    public View vProgressBg;

    public MyCourseViewHolder(View view, ControllerCourseItem controller) {
        super(view);
        ButterKnife.bind(this, view);

        this.mController = controller;
        this.mSelfView = view;
        this.mContext = InteractiveTrainingApp.getContext();
    }

    private void setupClickableViews(View selfView, final ControllerCourseItem controller) {
        this.ivCourseCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                controller.onCoverImageClick();
                Toast.makeText(mContext, R.string.ContentInDev, Toast.LENGTH_LONG).show();
            }
        });

        selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                controller.onTapCourse(mCourseVO);
                Toast.makeText(mContext, R.string.ContentInDev, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bindData(CourseVO courseVO) {
        mCourseVO = courseVO;

        GradientDrawable bgShape = (GradientDrawable) layoutImageCover.getBackground();
        bgShape.setColor(Color.parseColor(mCourseVO.getColorCode()));

        tvCourseTitle.setText(mCourseVO.getTitle());
        tvCategoryName.setText(mCourseVO.getCourseCategory().getCategoryName());
        tvCategoryName.setTextColor(Color.parseColor(mCourseVO.getColorCode()));

        String durationAndAuthor = mCourseVO.getDurationInMinute().toString() + " mins - Admin Team";
        tvDuration.setText(durationAndAuthor);

        Glide.with(ivCourseCoverImage.getContext())
                .load(R.drawable.uv_64)
                .asBitmap().centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivCourseCoverImage);

        setupClickableViews(mSelfView, mController);

//        Context context = ivCourseCoverImage.getContext();
//        int id = context
//                    .getResources()
//                    .getIdentifier("drawable-nodpi/" + courseVO.getImageUrl(), null, context.getPackageName());
//        ivCourseCoverImage.setImageResource(id);

//            Glide.with(ivCourseCoverImage.getContext())
//                    .load(imageUrl)
//                    .centerCrop()
//                    .placeholder(R.drawable.stock_photo_placeholder)
//                    .error(R.drawable.stock_photo_placeholder)
//                    .into(ivAttraction);
    }

    public static class LoadingCourseItemViewHolder extends MyCourseViewHolder {

        public LoadingCourseItemView loadingCourseItemView;

        public LoadingCourseItemViewHolder(LoadingCourseItemView view, ControllerCourseItem controller) {
            super(view, controller);
            this.loadingCourseItemView = view;
        }

        @Override
        public void bindData(CourseVO courseVO) {
            super.bindData(courseVO);
        }
    }

    public interface ControllerCourseItem {
        void onTapCourse(CourseVO course);
        void onCommentsClick(View v, int position);
        void onMoreClick(View v, int position);
        void onProfileClick(View v);

        void onCoverImageClick();
    }

}