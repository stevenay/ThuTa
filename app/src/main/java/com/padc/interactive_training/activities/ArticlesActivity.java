package com.padc.interactive_training.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.fragments.ArticleListFragment;
import com.padc.interactive_training.views.holders.ArticleViewHolder;

public class ArticlesActivity extends AppCompatActivity implements ArticleViewHolder.ControllerArticleItem {

    public static Intent newIntent() {
        Intent intent = new Intent(InteractiveTrainingApp.getContext(), ArticlesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                navigateToArticleDetailActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navigateToArticleListFragment();
        }
    }

    private void navigateToArticleDetailActivity(){
        Intent intentToArticleDetailActivity = ArticleDetailActivity.newIntent();
        startActivity(intentToArticleDetailActivity);
    }

    private void navigateToArticleListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, ArticleListFragment.newInstance())
                .commit();
    }

    //region Article Controller Item event(s)
    @Override
    public void onTapArticle(ArticleVO article) {
        Toast.makeText(InteractiveTrainingApp.getContext(), "You tap on article", Toast.LENGTH_SHORT);
        navigateToArticleDetailActivity();
    }

    @Override
    public void onCoverImageClick() {
        Toast.makeText(InteractiveTrainingApp.getContext(), "You tap on cover Image", Toast.LENGTH_SHORT);
    }
    //endregion
}
