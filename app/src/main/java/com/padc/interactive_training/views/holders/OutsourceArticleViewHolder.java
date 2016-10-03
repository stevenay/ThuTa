package com.padc.interactive_training.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.OutsourceArticleVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 10/3/2016.
 */
public class OutsourceArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_article_title)
    TextView tvArticleTilte;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.tv_outsource_name)
    TextView tvOutsourceName;

    @BindView(R.id.tv_author)
    TextView tvAuthorName;

    @BindView(R.id.iv_outsrouce_icon)
    ImageView ivOutsource;

    private ControllerArticleItem mControllerArticleItem;
    private OutsourceArticleVO mArticleVO;

    public OutsourceArticleViewHolder(View view, ControllerArticleItem controller) {
        super(view);
        ButterKnife.bind(this, view);

        this.mControllerArticleItem = controller;
        setupClickableViews(view, controller);
    }

    private void setupClickableViews(View selfView, final ControllerArticleItem controller) {
        this.ivOutsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onCoverImageClick();
            }
        });

        selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onTapArticle(mArticleVO);
            }
        });
    }

    public void bindData(OutsourceArticleVO articleVO){
        mArticleVO = articleVO;

        tvArticleTilte.setText(mArticleVO.getArticleTitle());
        tvPublishedDate.setText(mArticleVO.getPostDate());
        tvCategoryName.setText(mArticleVO.getCategoryName());
        tvOutsourceName.setText(mArticleVO.getOutsourceName());
        tvAuthorName.setText("Author: " + mArticleVO.getAuthorName());

        if (mArticleVO.getLogoUrl() == "medium")
            Glide.with(ivOutsource.getContext())
                    .load(R.drawable.medium)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivOutsource);
        else if (mArticleVO.getLogoUrl() == "wikihow")
            Glide.with(ivOutsource.getContext())
                    .load(R.drawable.wikihow)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivOutsource);
        else
            Glide.with(ivOutsource.getContext())
                    .load(R.drawable.google)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivOutsource);
    }

    @Override
    public void onClick(View view) {
        this.mControllerArticleItem.onTapArticle(this.mArticleVO);
    }

    public interface ControllerArticleItem{
        void onTapArticle(OutsourceArticleVO article);
        void onCoverImageClick();
    }

}
