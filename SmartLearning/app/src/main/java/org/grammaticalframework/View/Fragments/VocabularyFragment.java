package org.grammaticalframework.View.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grammaticalframework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VocabularyFragment extends BaseFragment implements View.OnClickListener  {

    NavController navController;

    public VocabularyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.vocabulary_button).setOnClickListener(this);
        view.findViewById(R.id.translateExercise_button).setOnClickListener(this);
    }

    @Override
    public void onClick (View v){
        switch (v.getId()){
            case R.id.vocabulary_button:
                navController.navigate(R.id.action_vocabularyFragment_to_synonymExerciseFragment);
                break;
            case R.id.translateExercise_button:
                navController.navigate(R.id.action_global_translateExerciseFragment);
                break;
        }
    }

}
