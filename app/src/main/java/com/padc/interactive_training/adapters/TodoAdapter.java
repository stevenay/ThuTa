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
<<<<<<< HEAD
import com.padc.interactive_training.data.vos.ChapterVO;
import com.padc.interactive_training.data.vos.TodoItemVO;
=======
>>>>>>> develop
import com.padc.interactive_training.views.holders.TodoItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/13/2016.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {

    private Context mContext;
<<<<<<< HEAD
    private List<TodoItemVO> mTodoItemList;

    public TodoAdapter(List<TodoItemVO> todoList) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mTodoItemList = todoList;
=======
    private ArrayList<String> mTodoList;

    public TodoAdapter(ArrayList<String> todoList) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mTodoList = todoList;
>>>>>>> develop
    }

    @Override
    public TodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_todo, parent, false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoItemViewHolder viewHolder, final int position) {
<<<<<<< HEAD
        viewHolder.bindData(mTodoItemList.get(position));
=======
        viewHolder.bindData(mTodoList.get(position));
>>>>>>> develop
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return mTodoItemList.size();
    }

    public void setNewData(List<TodoItemVO> todoItemList) {
        if (todoItemList != null) {
            mTodoItemList = todoItemList;
            notifyDataSetChanged();
        }
=======
        return mTodoList.size();
>>>>>>> develop
    }
}
