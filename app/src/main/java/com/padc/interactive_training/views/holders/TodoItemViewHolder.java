package com.padc.interactive_training.views.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.TodoItemVO;

/**
 * Created by NayLinAung on 9/26/2016.
 */
public class TodoItemViewHolder extends RecyclerView.ViewHolder {

    TextView textViewData;

    public TodoItemViewHolder(View itemView) {
        super(itemView);
        textViewData = (TextView) itemView.findViewById(R.id.text_data);
    }

    public void bindData(TodoItemVO item)
    {
        this.textViewData.setText(item.getDescription());
    }
}