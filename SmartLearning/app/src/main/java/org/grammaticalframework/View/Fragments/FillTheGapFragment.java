package org.grammaticalframework.View.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.FillTheGapViewModel;

import java.util.ArrayList;
import java.util.List;

public class FillTheGapFragment extends Fragment{

    final static private String TAG = FillTheGapFragment.class.getSimpleName();

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button reset;
    TextView sentence;
    TextView instruction;
    private boolean isCorrect = false;
    private Handler handlerCorrect;
    private Handler handlerIncorrect;

    TextView correctResult;
    TextView incorrectResult;

    FillTheGapViewModel model;
    NavController navController;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1 = getView().findViewById(R.id.button1));
        buttons.add(button2 = getView().findViewById(R.id.button2));
        buttons.add(button3 = getView().findViewById(R.id.button3));
        buttons.add(button4 = getView().findViewById(R.id.button4));
        buttons.add(button5 = getView().findViewById(R.id.button5));
        sentence = getView().findViewById(R.id.fillTheGapExercise);
        instruction = getView().findViewById(R.id.fillTheGapInstruction);

        reset = getView().findViewById(R.id.resetExercise);

        correctResult = getView().findViewById(R.id.fillTheGapCorrect);
        incorrectResult = getView().findViewById(R.id.fillTheGapIncorrect);

        navController = Navigation.findNavController(view);
        handlerCorrect = new Handler();
        handlerIncorrect = new Handler();

        model = new ViewModelProvider(requireActivity()).get(FillTheGapViewModel.class);
        model.getUnsolvedExercise().observe(getViewLifecycleOwner(), fillTheGapExercise -> {
            if(fillTheGapExercise == null)
                return;
            model.loadWord(fillTheGapExercise);
            List<String> inflections = model.getInflections();
            for(int i = 0; i < buttons.size() && i < inflections.size(); i++) {
                String word = inflections.get(i);
                Button btn = buttons.get(i);
                buttons.get(i).setText(word);
                buttons.get(i).setOnClickListener(v -> {
                    if(model.checkCorrectAnswer(word)){
                        sentence.setText(model.getSentence());
                        btn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

                        model.setCorrectAnswers(model.getCorrectAnswers() + 1);
                        correctResult.setText("Correct attempts: " + " " + model.getCorrectAnswers());

                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        handlerCorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                btn.getBackground().clearColorFilter();
                                navController.navigate(R.id.action_fillTheGapFragment_self);
                                model.getNewSentence();
                            }
                        }, 1500);
                    }else{
                        btn.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        model.setIncorrectAnswers(model.getIncorrectAnswers() + 1);
                        incorrectResult.setText("Incorrect attempts: " + " " + model.getIncorrectAnswers());
                        handlerIncorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.getBackground().clearColorFilter();
                            }
                        }, 1500);
                    }
                });
            }
            sentence.setText(model.getSentence());
            instruction.setText(model.getTense());
            correctResult.setText("Correct attempts: " + " " + model.getCorrectAnswers());
            incorrectResult.setText("Incorrect attempts: " + " " + model.getIncorrectAnswers());
        });

        reset.setOnClickListener(e -> {

        });

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        handlerCorrect.removeCallbacksAndMessages(null);
        handlerIncorrect.removeCallbacksAndMessages(null);
    }
}
