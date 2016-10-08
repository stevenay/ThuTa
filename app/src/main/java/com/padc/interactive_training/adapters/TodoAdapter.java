package com.padc.interactive_training.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.views.holders.TodoItemViewHolder;

import java.util.List;

/**
 * Created by NayLinAung on 9/13/2016.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {

    private Context mContext;
    private List<TodoItemVO> mTodoItemList;

    public TodoAdapter(List<TodoItemVO> todoList) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mTodoItemList = todoList;
    }

    @Override
    public TodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_todo, parent, false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoItemViewHolder viewHolder, final int position) {
        viewHolder.bindData(mTodoItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTodoItemList.size();
    }

    public void setNewData(List<TodoItemVO> todoItemList) {
        if (todoItemList != null) {
            mTodoItemList = todoItemList;
            notifyDataSetChanged();
        }
    }
}
