package com.padc.interactive_training.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;
import com.padc.interactive_training.adapters.ArticleAdapter;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.DateTimeUtils;
import com.padc.interactive_training.views.holders.ArticleViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListFragment extends Fragment {

    //region variable declaration
    private View view;
    private ArticleAdapter articleAdapter;
    private ArticleViewHolder.ControllerArticleItem controllerArticleItem;

    @BindView(R.id.rv_articles)
    RecyclerView rvArticle;
    //endregion

    public static ArticleListFragment newInstance(){
        ArticleListFragment fragment = new ArticleListFragment(); //ALFrag = Article List Fragment
        return fragment;
    }

    public ArticleListFragment() {

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
        articleAdapter = new ArticleAdapter(new ArrayList<ArticleVO>(), getContext(),controllerArticleItem);
        rvArticle.setAdapter(articleAdapter);

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

}
