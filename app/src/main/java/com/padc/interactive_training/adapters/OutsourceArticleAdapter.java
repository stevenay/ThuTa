package com.padc.interactive_training.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.OutsourceArticleVO;
import com.padc.interactive_training.views.holders.ArticleViewHolder;
import com.padc.interactive_training.views.holders.OutsourceArticleViewHolder;

import java.util.List;

/**
 * Created by NayLinAung on 10/3/2016.
 */
public class OutsourceArticleAdapter extends RecyclerView.Adapter<OutsourceArticleViewHolder>{
    private LayoutInflater inflater;
    private List<OutsourceArticleVO> mArticleList;

    private Context context;
    private OutsourceArticleViewHolder.ControllerArticleItem controllerArticleItem;

    private boolean showLoadingImage = false;
    //endregion


    public OutsourceArticleAdapter(List<OutsourceArticleVO> mArticleList, Context context, OutsourceArticleViewHolder.ControllerArticleItem controllerArticleItem) {
        this.mArticleList = mArticleList;
        this.context = context;
        this.controllerArticleItem = controllerArticleItem;
    }


    @Override
    public OutsourceArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_outsource_article, parent, false);
        final OutsourceArticleViewHolder articleViewHolder = new OutsourceArticleViewHolder(view, controllerArticleItem);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(OutsourceArticleViewHolder holder, int position) {
        holder.bindData(mArticleList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public void setNewData(List<OutsourceArticleVO> articleList) {
        if (articleList != null) {
            mArticleList = articleList;
            notifyDataSetChanged();
        }
    }
}
