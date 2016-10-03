package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ArticlePagerAdapter;
import com.padc.interactive_training.adapters.CoursePagerAdapter;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.OutsourceArticleVO;
import com.padc.interactive_training.fragments.ArticleListFragment;
import com.padc.interactive_training.fragments.CourseTodoListFragment;
import com.padc.interactive_training.fragments.DiscussionListFragment;
import com.padc.interactive_training.fragments.OutsourceArticleListFragment;
import com.padc.interactive_training.views.holders.ArticleViewHolder;
import com.padc.interactive_training.views.holders.OutsourceArticleViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesActivity extends AppCompatActivity
        implements ArticleViewHolder.ControllerArticleItem,
        OutsourceArticleViewHolder.ControllerArticleItem {

    @BindView(R.id.tl_navigations)
    SmartTabLayout tlNavigations;

    @BindView(R.id.pager_navigations)
    ViewPager pagerNavigations;

    private ArticlePagerAdapter mArticlePagerAdapter;

    public static Intent newIntent() {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), ArticlesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mArticlePagerAdapter = new ArticlePagerAdapter(getSupportFragmentManager());
        mArticlePagerAdapter.addTab(ArticleListFragment.newInstance(), "Our Owns");
        mArticlePagerAdapter.addTab(OutsourceArticleListFragment.newInstance(), "From others");

        pagerNavigations.setAdapter(mArticlePagerAdapter);
        pagerNavigations.setOffscreenPageLimit(mArticlePagerAdapter.getCount());

        tlNavigations.setViewPager(pagerNavigations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(800);
            getWindow().setEnterTransition(explode);

            Fade slide = new Fade();
            slide.setDuration(500);
            getWindow().setReturnTransition(slide);
        }
    }

    //region Article Controller Item event(s)
    @Override
    public void onTapArticle(ArticleVO article) {
        Intent intent = ArticleDetailActivity.newIntent(article.getArticleId());
        startActivity(intent);
    }

    @Override
    public void onTapArticle(OutsourceArticleVO article) {
        Intent intent = OutsourceArticleDetailActivity.newIntent(article.getOutsourceUrl(), article.getOutsourceName());
        startActivity(intent);
    }

    @Override
    public void onCoverImageClick() {
        Toast.makeText(InteractiveTrainingApp.getContext(), "You tap on cover Image", Toast.LENGTH_SHORT);
    }
    //endregion
}