package org.grammaticalframework.View.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grammaticalframework.R;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.ViewModel.FillTheGapViewModel;
import org.grammaticalframework.ViewModel.LexiconViewModel;

import java.util.List;

public class LexiconFragment extends Fragment{

    LexiconViewModel model;

    public LexiconFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(LexiconViewModel.class);
        final Observer<List<WNExplanation>> wneObserver = new Observer<List<WNExplanation>>() {
            @Override
            public void onChanged(List<WNExplanation> wnExplanations) {
                Log.d("Henrik", Integer.toString(wnExplanations.size()));
            }
        };
        model.wnExplanationLiveData.observe(getViewLifecycleOwner(), wneObserver);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lexicon, container, false);
    }

}
