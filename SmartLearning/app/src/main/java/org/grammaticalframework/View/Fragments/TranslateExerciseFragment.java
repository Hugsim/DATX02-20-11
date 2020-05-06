package org.grammaticalframework.View.Fragments;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.TranslateExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class TranslateExerciseFragment extends Fragment {
    final static private String TAG = SynonymExerciseFragment.class.getSimpleName();

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    TextView word;
    TextView instruction;

    TextView correctResult;
    TextView incorrectResult;
    TextView resultMessage;

    Button resetButton;

    private TranslateExerciseViewModel model;
    private NavController navController;

    private Handler handlerCorrect;
    private Handler handlerIncorrect;

    public TranslateExerciseFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_exercise_translate, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController((view));
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1 = getView().findViewById(R.id.button1));
        buttons.add(button2 = getView().findViewById(R.id.button2));
        buttons.add(button3 = getView().findViewById(R.id.button3));
        buttons.add(button4 = getView().findViewById(R.id.button4));
        buttons.add(button5 = getView().findViewById(R.id.button5));

        word = getView().findViewById(R.id.translateExercise);
        instruction = getView().findViewById(R.id.translateInstruction);

        resetButton = getView().findViewById(R.id.resetTranslateExercise);

        correctResult = getView().findViewById(R.id.translateCorrect);
        incorrectResult = getView().findViewById(R.id.translateIncorrect);
        resultMessage = getView().findViewById(R.id.resultMessage);

        handlerCorrect = new Handler();
        handlerIncorrect = new Handler();

        model = new ViewModelProvider(requireActivity()).get(TranslateExerciseViewModel.class);

        model.getUnsolvedExercise().observe(getViewLifecycleOwner(), translateExercise -> {
            if (translateExercise == null){
                return;
            }
            model.loadWord(translateExercise);
            List<String> alternatives = model.getAlternatives();
            for(int i = 0; i < buttons.size() && i < alternatives.size(); i++) {
                String word = alternatives.get(i);
                Button btn = buttons.get(i);
                buttons.get(i).setText(word);

                resetButton.setOnClickListener(v ->{
                    model.setCorrectAnswers(0);
                    model.setIncorrectAnswers(0);
                    navController.popBackStack(R.id.vocabularyFragment, false);
                });

                buttons.get(i).setOnClickListener(v -> {
                    if(model.checkCorrectAnswer(word)){
                        btn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                        model.setCorrectAnswers(model.getCorrectAnswers() + 1);
                        correctResult.setText("Correct attempts: " + " " + model.getCorrectAnswers());
                        resultMessage.setText("Great!");
                        resultMessage.setVisibility(View.VISIBLE);
                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        handlerCorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultMessage.setVisibility(View.INVISIBLE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                btn.getBackground().clearColorFilter();
                                navController.navigate(R.id.action_translateExerciseFragment_self);
                                model.getNewExercise();
                            }
                        }, 1500);
                    }else{
                        resultMessage.setText("Try again!");
                        resultMessage.setVisibility(View.VISIBLE);
                        btn.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        model.setIncorrectAnswers(model.getIncorrectAnswers() + 1);
                        incorrectResult.setText("Incorrect attempts: " + " " + model.getIncorrectAnswers());
                        handlerIncorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultMessage.setVisibility(View.INVISIBLE);
                                btn.getBackground().clearColorFilter();
                            }
                        }, 1500);
                    }
                });
            }
            word.setText(model.getWord());
        });
        instruction.setText("Choose the correct translation");
        correctResult.setText("Correct attempts: " + " " + model.getCorrectAnswers());
        incorrectResult.setText("Incorrect attempts: " + " " + model.getIncorrectAnswers());
        resetButton.setText("Finish");
    }
}
