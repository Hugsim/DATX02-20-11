package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.grammaticalframework.R;

public class MainExerciseFragment extends BaseFragment implements View.OnClickListener {
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.vocabulary_button).setOnClickListener(this);
        view.findViewById(R.id.grammar_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grammar_button:
                navController.navigate(R.id.action_mainExerciseFragment_to_grammarFragment);
                break;
            case R.id.vocabulary_button:
                navController.navigate(R.id.action_mainExerciseFragment_to_vocabularyFragment);
        }
    }
}