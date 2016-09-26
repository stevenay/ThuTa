package com.padc.interactive_training.views.holders;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.padc.interactive_training.R;

/**
 * Created by NayLinAung on 9/26/2016.
 */
public class CourseTodoItemViewHolder extends RecyclerView.ViewHolder {

    SwipeLayout swipeLayout;
    TextView textViewData;
    TextView tvDoneLabel;
    LinearLayout layoutSave;
    LinearLayout layoutDelete;

    private ControllerTodoItem mController;

    public CourseTodoItemViewHolder(View itemView, ControllerTodoItem controllerTodoItem) {
        super(itemView);
        this.mController = controllerTodoItem;

        swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_todo);
        textViewData = (TextView) itemView.findViewById(R.id.text_data);
        tvDoneLabel = (TextView) itemView.findViewById(R.id.tv_done_label);
        layoutSave = (LinearLayout) itemView.findViewById(R.id.layout_save);
        layoutDelete = (LinearLayout) itemView.findViewById(R.id.layout_delete);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindData(String item, final int position)
    {
        this.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.onTapDelete(position, swipeLayout);
            }
        });

        this.layoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.onTapDone(position, swipeLayout);
                swipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (tvDoneLabel.getText().toString().toLowerCase().equals("done")) {
                            textViewData.setPaintFlags(textViewData.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tvDoneLabel.setText("Undo");
                        } else {
                            textViewData.setPaintFlags(textViewData.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                            tvDoneLabel.setText("Done");
                        }
                    }
                }, 50);
            }
        });

        this.textViewData.setText(item);
    }

    public interface ControllerTodoItem {
        void onTapDelete(int positionOfItem, SwipeLayout deletedLayout);
        void onTapDone(int positionOfItem, SwipeLayout doneLayout);
    }
}