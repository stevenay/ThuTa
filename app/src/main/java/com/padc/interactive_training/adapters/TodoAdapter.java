package com.padc.interactive_training.adapters;

import android.content.Context;
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
import com.padc.interactive_training.views.holders.TodoItemViewHolder;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 9/13/2016.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {

    private Context mContext;
    private ArrayList<String> mTodoList;

    public TodoAdapter(ArrayList<String> todoList) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mTodoList = todoList;
    }

    @Override
    public TodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_todo, parent, false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoItemViewHolder viewHolder, final int position) {
        viewHolder.bindData(mTodoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }
}
