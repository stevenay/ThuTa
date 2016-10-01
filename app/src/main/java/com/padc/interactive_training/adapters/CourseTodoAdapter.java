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
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.data.vos.TodoItemVO;
import com.padc.interactive_training.views.holders.CourseTodoItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/13/2016.
 */
public class CourseTodoAdapter extends RecyclerSwipeAdapter<CourseTodoItemViewHolder>
    implements CourseTodoItemViewHolder.ControllerTodoItem {

    private Context mContext;
    private List<TodoItemVO> mTodoItemList;

    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public CourseTodoAdapter(List<TodoItemVO> todoItemList) {
        this.mContext = InteractiveTrainingApp.getContext();
        this.mTodoItemList = todoItemList;
    }

    @Override
    public CourseTodoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_course_todo, parent, false);
        return new CourseTodoItemViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final CourseTodoItemViewHolder viewHolder, final int position) {
        TodoItemVO item = mTodoItemList.get(position);
        viewHolder.bindData(item, position);
        mItemManger.bindView(viewHolder.itemView, position);
    }

    public TodoItemVO getItem(int position) {
        return mTodoItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mTodoItemList.size();
    }

    public void setNewData(List<TodoItemVO> newList) {
        if (newList != null) {
            mTodoItemList = newList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_todo;
    }

    //region Controller Interface Implementation
    @Override
    public void onTapDelete(int positionOfItem, SwipeLayout deletedLayout) {
        mItemManger.removeShownLayouts(deletedLayout);
        mTodoItemList.remove(positionOfItem);
        notifyItemRemoved(positionOfItem);
        notifyItemRangeChanged(positionOfItem, mTodoItemList.size());
        mItemManger.closeAllItems();
//        Toast.makeText(mContext, "Removed 1 item!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTapDone(int positionOfItem, SwipeLayout doneLayout) {
        mItemManger.closeAllItems();
//        Toast.makeText(mContext, "Done 1 item!", Toast.LENGTH_SHORT).show();
    }
    //endregion
}
