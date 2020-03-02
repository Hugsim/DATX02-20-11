package org.grammaticalframework.View.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.grammaticalframework.R;

public class FillTheGapFragment extends Fragment{
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    TextView sentence;

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
        button1 = getView().findViewById(R.id.button1);
        button2 = getView().findViewById(R.id.button2);
        button3 = getView().findViewById(R.id.button3);
        button4 = getView().findViewById(R.id.button4);
        button5 = getView().findViewById(R.id.button5);
        sentence = getView().findViewById(R.id.exerciseSentence);
<<<<<<< Updated upstream
=======

        model = new ViewModelProvider(requireActivity()).get(FillTheGapViewModel.class);
        ArrayList<String> inflections = model.getInflections();
        for(int i = 0; i < buttons.size() && i < inflections.size(); i++) {
            String word = inflections.get(i);
            buttons.get(i).setText(word);
            buttons.get(i).setOnClickListener(v -> {
                Toast toast;
                if(model.checkCorrectAnswer(word)){
                    toast = Toast.makeText(getActivity(), "Correct answer!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View toastCorrectView = toast.getView();
                    toastCorrectView.setBackgroundColor(Color.rgb(138,227,113));
                }else{
                    toast = Toast.makeText(getActivity(), "Wrong answer!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0 );
                    View toastCorrectView = toast.getView();
                    toastCorrectView.setBackgroundColor(Color.rgb(227,113,113));
                }
                toast.show();

            });
        }

        sentence.setText(model.getSentence());


>>>>>>> Stashed changes
    }
}
