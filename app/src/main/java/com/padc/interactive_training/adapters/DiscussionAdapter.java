package com.padc.interactive_training.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.DiscussionVO;
import com.padc.interactive_training.views.holders.DiscussionViewHolder;

import java.util.List;

/**
 * Created by NayLinAung on 9/16/2016.
 */
public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionViewHolder> {

    private LayoutInflater mInflater;
    private List<DiscussionVO> mDiscussionList;
    private DiscussionViewHolder.ControllerDiscussionItem mControllerDiscussionItem;

    public DiscussionAdapter(List<DiscussionVO> discussionList, DiscussionViewHolder.ControllerDiscussionItem controllerDiscussionItem) {
        mInflater = LayoutInflater.from(InteractiveTrainingApp.getContext());
        mDiscussionList = discussionList;
        mControllerDiscussionItem = controllerDiscussionItem;
    }

    @Override
    public DiscussionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.view_item_discussion, parent, false);
        return new DiscussionViewHolder(itemView, mControllerDiscussionItem);
    }

    @Override
    public void onBindViewHolder(DiscussionViewHolder holder, int position) {
        holder.bindData(mDiscussionList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDiscussionList.size();
    }

    public void setNewData(List<DiscussionVO> newDisucssionList) {
        if (newDisucssionList != null) {
            mDiscussionList = newDisucssionList;
            notifyDataSetChanged();
        }
    }
}