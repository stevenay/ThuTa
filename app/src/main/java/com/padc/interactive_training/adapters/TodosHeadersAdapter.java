package com.padc.interactive_training.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.views.holders.CourseTodoItemViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/26/2016.
 */
public class TodosHeadersAdapter extends CourseTodoAdapter
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    public TodosHeadersAdapter(List<TodoItemVO> todoItem) {
        super(todoItem);
    }

    @Override
    public CourseTodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_course_todo, parent, false);
        return new CourseTodoItemViewHolder(view, this) {};
    }

    @Override
    public long getHeaderId(int position) {
        return Long.parseLong(getItem(position).getTodoListId());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_header_course_todo, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tvListTitle = (TextView) holder.itemView.findViewById(R.id.tv_list_title);
        TextView tvFromChapter = (TextView) holder.itemView.findViewById(R.id.tv_from_chapter);

        tvListTitle.setText(getItem(position).getTodoListName());
        tvFromChapter.setText("(From Chapter 3 - Be true to brand identity)");
    }
}