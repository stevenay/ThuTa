package com.padc.interactive_training.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 9/13/2016.
 */
public class CourseTodoAdapter extends RecyclerSwipeAdapter<CourseTodoAdapter.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewData;
        TextView tvDoneLabel;
        LinearLayout layoutSave;
        LinearLayout layoutDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);

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
    }

    private Context mContext;
    private ArrayList<String> mDataset;

    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public CourseTodoAdapter(ArrayList<String> objects) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_course_todo, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.iv_save));
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.iv_trash));
            }
        });

        viewHolder.swipeLayout.setOnClickListener(new SwipeLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SwipeLayout layout = (SwipeLayout) view;
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.toggle();
                    }
                }, 50);
            }
        });

        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(final SwipeLayout layout, boolean surface) {
//                layout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        layout.toggle();
//                    }
//                }, 50);
            }
        });

        viewHolder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                // + viewHolder.textViewData.getText().toString() + "!"
                Toast.makeText(view.getContext(), "Removed 1 item!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.layoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Done 1 item!", Toast.LENGTH_SHORT).show();
                viewHolder.layoutSave.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewHolder.tvDoneLabel.getText().toString().toLowerCase().equals("done")) {
                            viewHolder.textViewData.setPaintFlags(viewHolder.textViewData.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            viewHolder.tvDoneLabel.setText("Undo");
                        } else {
                            viewHolder.textViewData.setPaintFlags(viewHolder.textViewData.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                            viewHolder.tvDoneLabel.setText("Done");
                        }
                    }
                }, 50);
            }
        });

        viewHolder.textViewData.setText(item);
        mItemManger.bindView(viewHolder.itemView, position);
    }

    public String getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_todo;
    }
}
