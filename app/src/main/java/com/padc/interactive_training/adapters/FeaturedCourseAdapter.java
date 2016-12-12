package com.padc.interactive_training.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.CourseVO;
import com.padc.interactive_training.views.holders.FeaturedCourseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.padc.interactive_training.adapters.MyCourseAdapter.VIEW_TYPE_LOADER;

/**
 * Created by NayLinAung on 9/23/2016.
 */
public class FeaturedCourseAdapter extends RecyclerView.Adapter<FeaturedCourseViewHolder> {
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_FEATURED = 2;

    private List<CourseVO> mCourseList;
    private Context context;
    private FeaturedCourseViewHolder.ControllerFeaturedCourseItem controllerCourseItem;

    public FeaturedCourseAdapter(FeaturedCourseViewHolder.ControllerFeaturedCourseItem controllerCourseItem) {
        this.controllerCourseItem = controllerCourseItem;

        this.context = InteractiveTrainingApp.getContext();
        this.mCourseList = new ArrayList<>();
    }

    @Override
    public FeaturedCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_featured_course, parent, false);
        FeaturedCourseViewHolder cellCourseViewHolder = new FeaturedCourseViewHolder(view, controllerCourseItem);
        return cellCourseViewHolder;
    }

    @Override
    public void onBindViewHolder(FeaturedCourseViewHolder viewHolder, int position) {
        viewHolder.bindData(this.mCourseList.get(position));
        if (getItemViewType(position) == VIEW_TYPE_FEATURED) {
            viewHolder.bindFeaturedData();
        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_FEATURED;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    public void setNewData(List<CourseVO> newCourseList) {
        if (newCourseList != null) {
            mCourseList = newCourseList;
            notifyDataSetChanged();
        }
    }
}
