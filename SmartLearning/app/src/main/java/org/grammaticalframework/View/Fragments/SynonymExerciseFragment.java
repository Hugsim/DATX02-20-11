package org.grammaticalframework.View.Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
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
import org.grammaticalframework.ViewModel.SynonymExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class SynonymExerciseFragment  extends Fragment {
    final static private String TAG = SynonymExerciseFragment.class.getSimpleName();

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    TextView word;
    TextView instruction;

    private SynonymExerciseViewModel model;
    private NavController navController;

    private Handler handlerCorrect;
    private Handler handlerIncorrect;

    public SynonymExerciseFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_exercise_synonym, container, false);
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
        word = getView().findViewById(R.id.fillTheGapExercise);
        instruction = getView().findViewById(R.id.synonymInstruction);

        handlerCorrect = new Handler();
        handlerIncorrect = new Handler();

        model = new ViewModelProvider(requireActivity()).get(SynonymExerciseViewModel.class);

        model.getUnsolvedExercise().observe(getViewLifecycleOwner(), synonymExercise -> {
            if (synonymExercise == null)
                return;
            model.loadWord(synonymExercise);
            List<String> alternatives = model.getAlternatives();
            for(int i = 0; i < buttons.size() && i < alternatives.size(); i++) {
                String word = alternatives.get(i);
                Button btn = buttons.get(i);
                buttons.get(i).setText(word);
                buttons.get(i).setOnClickListener(v -> {
                    if(model.checkCorrectAnswer(word)){
                        btn.setBackgroundResource(R.drawable.button_green);
                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        handlerCorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                btn.setBackgroundResource(R.drawable.buttons);
                                navController.navigate(R.id.action_synonymExerciseFragment_self);
                                model.getNewExercise();
                            }
                        }, 1500);
                    }else{
                        btn.setBackgroundResource(R.drawable.button_red);
                        handlerIncorrect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setBackgroundResource(R.drawable.buttons);
                            }
                        }, 1500);
                    }
                });
            }
            word.setText(model.getWord());
            instruction.setText("Choose the correct synonym");
        });

    }
}
