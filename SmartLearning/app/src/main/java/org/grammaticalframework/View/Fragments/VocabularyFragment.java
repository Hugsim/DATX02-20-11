package org.grammaticalframework.View.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grammaticalframework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VocabularyFragment extends BaseFragment {


    public VocabularyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary, container, false);


    }

}
