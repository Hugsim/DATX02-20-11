package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.grammaticalframework.R;
import org.grammaticalframework.View.MainActivity;
import org.grammaticalframework.ViewModel.FillTheGapViewModel;

import java.util.ArrayList;

public class FillTheGapFragment extends Fragment{
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    TextView sentence;

    FillTheGapViewModel model;

    public FillTheGapFragment () {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_exercise_fill_the_gap, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1 = getView().findViewById(R.id.button1));
        buttons.add(button2 = getView().findViewById(R.id.button2));
        buttons.add(button3 = getView().findViewById(R.id.button3));
        buttons.add(button4 = getView().findViewById(R.id.button4));
        buttons.add(button5 = getView().findViewById(R.id.button5));
        sentence = getView().findViewById(R.id.exerciseSentence);

        model = new ViewModelProvider(requireActivity()).get(FillTheGapViewModel.class);
        ArrayList<String> inflections = model.getInflections();
        for(int i = 0; i < buttons.size() && i < inflections.size(); i++) {
            String word = inflections.get(i);
            buttons.get(i).setText(word);
            buttons.get(i).setOnClickListener(v -> {
                Toast toast;
                if(model.checkCorrectAnswer(word)){
                    toast = Toast.makeText(getActivity(), "Correct answer!", Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(getActivity(), "Wrong answer!", Toast.LENGTH_SHORT);
                }
                toast.show();

            });
        }

        sentence.setText(model.getSentence());


    }
}