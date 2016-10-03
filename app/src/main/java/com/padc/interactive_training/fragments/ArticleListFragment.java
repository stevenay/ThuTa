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

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_articles)
    RecyclerView rvArticle;

    //region variable declaration
    private View view;
    private ArticleAdapter mArticleAdapter;
    private ArticleViewHolder.ControllerArticleItem controllerArticleItem;
    //endregion

    public ArticleListFragment() {

    }

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
        }
        else{
            throw new RuntimeException(context.toString()+ "must implement ArticleViewHolder.ControllerArticleItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);

        bindArticleListData();

        return view;
    }

    private void bindArticleListData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state){
                return 300;
            }
        };
        rvArticle.setLayoutManager(linearLayoutManager);
        mArticleAdapter = new ArticleAdapter(new ArrayList<ArticleVO>(), getContext(), controllerArticleItem);
        rvArticle.setAdapter(mArticleAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000); // ms
            }
        });
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
        List<ArticleVO> attractionList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                ArticleVO attraction = ArticleVO.parseFromCursor(data);
                attractionList.add(attraction);
            } while (data.moveToNext());
        }

        bindArticleListData();
        Log.d(InteractiveTrainingApp.TAG, "Retrieved attractions DESC : " + attractionList.size());
        mArticleAdapter.setNewData(attractionList);

        ArticleModel.getInstance().setStoredArticleData(attractionList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}