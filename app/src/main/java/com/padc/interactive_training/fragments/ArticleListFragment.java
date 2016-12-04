package com.padc.interactive_training.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ArticleAdapter;
import com.padc.interactive_training.data.models.ArticleModel;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.views.holders.ArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_articles)
    RecyclerView rvArticle;

    @BindView(R.id.circle_progress_bar)
    ProgressBar pbCircle;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private View view;
    private ArticleAdapter mArticleAdapter;
    private ArticleViewHolder.ControllerArticleItem controllerArticleItem;

    public static ArticleListFragment newInstance(){
        ArticleListFragment fragment = new ArticleListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(InteractiveTrainingConstants.ARTICLE_LIST_LOADER, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArticleViewHolder.ControllerArticleItem){
            controllerArticleItem = (ArticleViewHolder.ControllerArticleItem) context;
        } else {
            throw new RuntimeException(context.toString()+ "must implement ArticleViewHolder.ControllerArticleItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);

        setLayoutManagerOfArticleRecyclerView();
        setAdapterToArticleRecyclerView();

        ArticleModel.getInstance().loadArticles();
        return view;
    }

    private void setLayoutManagerOfArticleRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state){
                return 300;
            }
        };
        rvArticle.setLayoutManager(linearLayoutManager);
    }

    private void setAdapterToArticleRecyclerView() {
        mArticleAdapter = new ArticleAdapter(controllerArticleItem);
        rvArticle.setAdapter(mArticleAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                CoursesContract.ArticleEntry.CONTENT_URI,
                null, // projection - {"name", "location"}
                null, // selection - "region = ? AND popular = ?"
                null, // selectionArgs - {"upper_myanmar", "very_high"}
                CoursesContract.ArticleEntry.COLUMN_ARTICLE_ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ArticleVO> articleList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                ArticleVO article = ArticleVO.parseFromCursor(data);
                articleList.add(article);
            } while (data.moveToNext());
        }

        showLoaderSpinner(articleList);

        mArticleAdapter.setNewData(articleList);
        ArticleModel.getInstance().setStoredArticleData(articleList);
    }

    private void showLoaderSpinner(List<ArticleVO> data) {
        // Decide articles existed in the database
        if (data.size() <= 0) {
            pbCircle.setVisibility(View.VISIBLE);
        } else {
            pbCircle.setVisibility(View.INVISIBLE);
            rvArticle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}