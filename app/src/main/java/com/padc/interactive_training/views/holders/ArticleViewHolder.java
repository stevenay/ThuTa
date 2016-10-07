package com.padc.interactive_training.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.DateTimeUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by htoomt on 9/17/2016.
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.tv_article_title)
    TextView tvArticleTilte;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.tv_author)
    TextView tvAuthor;

    @BindView(R.id.tv_article_brief_description)
    TextView tvArticleBriefDescription;

    @BindView(R.id.iv_article)
    ImageView ivArticle;

    private ControllerArticleItem mControllerArticleItem;
    private ArticleVO mArticleVO;

    public ArticleViewHolder(View view, ControllerArticleItem controller) {
        super(view);
        ButterKnife.bind(this, view);

        this.mControllerArticleItem = controller;
        setupClickableViews(view, controller);
    }

    private void setupClickableViews(View selfView, final ControllerArticleItem controller) {
        this.ivArticle.setOnClickListener(new View.OnClickListener() {
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

    public void bindData(ArticleVO articleVO){
        mArticleVO = articleVO;

        String publishedDate = mArticleVO.getPublishedDateTime();
        Date covertPublishedDate = DateTimeUtils.parseStringToDateTime(publishedDate);
        String formmatedDate = DateTimeUtils.parseDateToString(covertPublishedDate);

        tvArticleTilte.setText(mArticleVO.getArticleTitle());
        tvPublishedDate.setText(formmatedDate);
        tvCategoryName.setText("Technology");
        tvAuthor.setText("Author: " + mArticleVO.getAuthor() + " ,");
        tvArticleBriefDescription.setText(mArticleVO.getIntroContent());

        Glide.with(ivArticle.getContext())
                .load(mArticleVO.getImageUrl1())
                .centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivArticle);

    }

    @Override
    public void onClick(View view) {
        this.mControllerArticleItem.onTapArticle(this.mArticleVO);
    }

    public interface ControllerArticleItem{
        void onTapArticle(ArticleVO article);
        void onCoverImageClick();
    }
}