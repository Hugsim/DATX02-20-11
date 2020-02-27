package org.grammaticalframework.View.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grammaticalframework.R;

public class Exercise_nav_graph_Fragment extends BaseFragment {

    public Exercise_nav_graph_Fragment(String tag) {
        super(tag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_navgraph, container, false);
    }
}
