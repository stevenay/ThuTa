package com.padc.interactive_training.views.holders;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ArticleVO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

//        Date publishedDate = mArticleVO.getPublishedDate();
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String strPublishedDate = df.format(publishedDate);
//
//        tvArticleTilte.setText(mArticleVO.getTitle());
//        tvPublishedDate.setText(strPublishedDate);
//        tvCategoryName.setText(mArticleVO.getCategoryName());
//        tvArticleBriefDescription.setText(mArticleVO.getBriefDescription());
//        ivArticle.setImageResource(mArticleVO.getImageURL());

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
