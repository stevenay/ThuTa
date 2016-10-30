package com.padc.interactive_training.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.interactive_training.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiceFragment extends Fragment {

    public static MultipleChoiceFragment newInstance() {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_choice, container, false);
    }

}
