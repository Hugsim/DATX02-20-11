package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.grammaticalframework.R;

public class MainExerciseFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_exercises, container, false);
        return v;

    }
    
    public void buttonClick(View view) {
        int id = view.getId();
        Toast toast;

        switch (id){
            case R.id.grammar_button:
                // Intent intent = new Intent(this, GrammarActivity.class);
                toast = Toast.makeText(getActivity(), "Go to grammar", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.vocabulary_button:
                // Intent intent = new Intent(this, WordsActivity.class);
                toast = Toast.makeText(getActivity(), "Go to vocabulary", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}