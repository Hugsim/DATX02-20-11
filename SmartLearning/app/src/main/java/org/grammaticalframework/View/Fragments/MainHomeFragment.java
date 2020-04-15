package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.grammaticalframework.Language;
import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.ViewModelProvider;

public class MainHomeFragment extends BaseFragment {
    private Spinner fromSpinner;
    private Spinner toSpinner;
    HomeViewModel homeVM;
    private TextView triviaTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeVM = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_home, container, false);
        homeVM.fillTriviaList();
        triviaTextView = v.findViewById(R.id.home_page_trivia);
        setTriviaText();
        fromSpinner =  v.findViewById(R.id.homeFromSpinner);
        toSpinner = v.findViewById(R.id.homeToSpinner);
        // Lower the dropdown menu so that the currently selected item can be seen
        toSpinner.setDropDownVerticalOffset(100);
        fromSpinner.setDropDownVerticalOffset(100);

        // Adapter for list items to show in recycler view
        ArrayAdapter<Language> spinnerLanguages = new ArrayAdapter<>(getActivity(), R.layout.spinner_item2, homeVM.getAvailableLanguages());
        spinnerLanguages.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        fromSpinner.setAdapter(spinnerLanguages);
        toSpinner.setAdapter(spinnerLanguages);

        // Set initial languages in spinners and text
        fromSpinner.setSelection(homeVM.getLanguageIndex(homeVM.getSourceLanguage()));
        toSpinner.setSelection(homeVM.getLanguageIndex(homeVM.getTargetLanguage()));

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Language lang = (Language) parent.getSelectedItem();
                homeVM.setSourceLanguage(lang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Language lang = (Language) parent.getSelectedItem();
                homeVM.setTargetLanguage(lang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;

    }

    private void setTriviaText(){
        Random r = new Random();
        int i = r.nextInt(homeVM.getTriviaList().size());
        triviaTextView.setText(homeVM.getTriviaList().get(i));
    }

}
