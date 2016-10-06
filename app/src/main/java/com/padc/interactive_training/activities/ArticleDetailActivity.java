package com.padc.interactive_training.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.DateTimeUtils;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //region variable declaration
    private final static String IE_ARTICLE_ID = "IE_ARTICLE_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolBar;

    @BindView(R.id.tv_article_title)
    TextView tvArticleTitle;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.iv_header_photo)
    ImageView ivHeaderPhoto;

    @BindView(R.id.iv_stock_photo)
    ImageView ivStockPhoto;

    @BindView(R.id.iv_stock_photo_2)
    ImageView ivStockPhoto2;

    @BindView(R.id.tv_author_name)
    TextView tvAuthorName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_introduction_content)
    TextView tvIntroContent;

    @BindView(R.id.tv_first_heading)
    TextView tvFirstHeading;

    @BindView(R.id.tv_second_heading)
    TextView tvSecondHeading;

    @BindView(R.id.tv_first_heading_content)
    TextView tvFirstHeadingContent;

    @BindView(R.id.tv_second_heading_content)
    TextView tvSecondHeadingContent;

    @BindView(R.id.tv_third_heading)
    TextView tvThirdHeading;

    @BindView(R.id.tv_third_heading_content)
    TextView tvThirdHeadingContent;

    private int mArticleId;
    private ArticleVO mArticle;

    //endregion

    public static Intent newIntent(int articleId) {
        Intent newIntent = new Intent(InteractiveTrainingApp.getContext(), ArticleDetailActivity.class);
        newIntent.putExtra(IE_ARTICLE_ID, articleId);
        return newIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this, this);
        //setContentView(R.layout.true_false_question);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mArticleId = getIntent().getIntExtra(IE_ARTICLE_ID, 0);

        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.ARTICLE_DETAIL_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);

        return true;
    }

    //region loader pattern methods
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.ArticleEntry.buildArticleUriWithId(mArticleId),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mArticle = ArticleVO.parseFromCursor(data);

            bindData(mArticle);
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //endregion

    private void bindData(ArticleVO mArticle) {

        tvCategoryName.setText("နည္းပညာ");
        tvAuthorName.setText(mArticle.getAuthor());

        String publishedDate = mArticle.getPublishedDateTime();
        Date covertPublishedDate = DateTimeUtils.parseStringToDateTime(publishedDate);
        String formmatedDate = DateTimeUtils.parseDateToString(covertPublishedDate);
        tvPublishedDate.setText(formmatedDate);

        tvIntroContent.setText(mArticle.getIntroContent());

        tvFirstHeading.setText(mArticle.getFirstHeading());
        tvSecondHeading.setText(mArticle.getSecondHeading());
        tvThirdHeading.setText(mArticle.getThirdHeading());

        tvFirstHeadingContent.setText(mArticle.getFirstHeadingContent());
        tvSecondHeadingContent.setText(mArticle.getSecondHeadingContent());
        tvThirdHeadingContent.setText(mArticle.getThirdHeadingContent());

        collapsingToolBar.setTitle("သုတေဆာင္းပါး");
        tvArticleTitle.setText(mArticle.getArticleTitle());
        if (mArticle.getArticleTitle().contains("မူးယစ္စြာေမာင္းႏွင္ျခင္းႏွင့္ မေတာ္တ")) {
            Glide.with(ivHeaderPhoto.getContext())
                    .load(R.drawable.ic_car)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivHeaderPhoto);
        } else {
            Glide.with(ivHeaderPhoto.getContext())
                    .load(R.drawable.ic_building)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivHeaderPhoto);
        }


        Glide.with(ivStockPhoto.getContext())
                .load(mArticle.getImageUrl1())
                .centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivStockPhoto);

        Glide.with(ivStockPhoto2.getContext())
                .load(mArticle.getImageUrl2())
                .centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivStockPhoto2);
    }
}