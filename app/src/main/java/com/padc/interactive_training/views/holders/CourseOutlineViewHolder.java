package com.padc.interactive_training.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.TodoItemVO;

/**
 * Created by NayLinAung on 10/26/2016.
 */

public class CourseOutlineViewHolder extends RecyclerView.ViewHolder {

    TextView tvCourseOutlineNumber;
    TextView tvCourseOutlineDesp;

    public CourseOutlineViewHolder(View itemView) {
        super(itemView);
        tvCourseOutlineNumber = (TextView) itemView.findViewById(R.id.tv_course_outline_number);
        tvCourseOutlineDesp = (TextView) itemView.findViewById(R.id.tv_course_outline_desp);
    }

    public void bindData(int outlineNumber,String outlineDescription)
    {
        this.tvCourseOutlineDesp.setText(outlineDescription);
        this.tvCourseOutlineNumber.setText(String.valueOf(outlineNumber) + ".");
    }
}
