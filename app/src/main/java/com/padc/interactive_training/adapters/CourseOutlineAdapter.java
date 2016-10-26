package com.padc.interactive_training.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.views.holders.CourseOutlineViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 10/26/16.
 */
public class CourseOutlineAdapter extends RecyclerView.Adapter<CourseOutlineViewHolder> {

    private List<String> outlineList;
    private LayoutInflater inflater;

    public CourseOutlineAdapter(List<String> outlineList) {
        if (outlineList != null) {
            this.outlineList = outlineList;
        } else {
            this.outlineList = new ArrayList<>();
        }
        inflater = LayoutInflater.from(InteractiveTrainingApp.getContext());
    }

    @Override
    public CourseOutlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.view_item_course_outline, parent, false);
        return new CourseOutlineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseOutlineViewHolder holder, int position) {
        holder.bindData(position+1, outlineList.get(position));
    }

    @Override
    public int getItemCount() {
        return outlineList.size();
    }


    public void setNewData(List<String> newCourseOutlineList) {
        outlineList = newCourseOutlineList;
        notifyDataSetChanged();
    }
}
