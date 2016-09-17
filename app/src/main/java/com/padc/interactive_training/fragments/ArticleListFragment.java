package com.padc.interactive_training.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListFragment extends Fragment {
    private View view;

    public ArticleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_list, container, false);

        //RecyclerView rvArticle = (RecyclerView) view.findViewById(R.id.rv_articles);


        return view;
    }
}
