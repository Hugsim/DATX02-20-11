package org.grammaticalframework.View.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import org.grammaticalframework.Language;
import org.grammaticalframework.R;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.Repository.WNExplanationWithCheck;
import org.grammaticalframework.View.FragmentFactory;
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.ViewModel.LexiconWordAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainLexiconFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener {

    private LexiconViewModel lexiconVM;
    private final BaseFragment lexiconDetailsFragment = FragmentFactory.createLexiconDetailsFragment();
    private EditText search_bar;
    private AppBarLayout lexicon_toolbar;
    private Spinner fromLanguageSpinner;
    private Spinner toLanguageSpinner;
    private ImageButton switch_button;
    private ImageButton expand_button;
    private ImageButton search_clear_button;
    private TextView from_lang_short;
    private TextView to_lang_short;
    private ImageView dropDown_icon;
    private String searchString;
    private RecyclerView rvLexicon;
    private LexiconWordAdapter wordAdapter;

    private int listSize;
    private boolean appBarExpanded = false;
    private boolean appBarCollapsed = true;
    private float fromOffsetX;
    private float fromOffsetY;
    private float toOffsetX;
    private float toOffsetY;
    private float switchOffsetX;
    private float switchOffsetY;
    private boolean updateVM;
    private static final String TAG = MainLexiconFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lexiconVM = new ViewModelProvider(getActivity()).get(LexiconViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon, container, false);

        search_bar = fragmentView.findViewById(R.id.lexicon_searchbar);
        fromLanguageSpinner = fragmentView.findViewById(R.id.lexicon_from_language);
        toLanguageSpinner = fragmentView.findViewById(R.id.lexicon_to_language);
        lexicon_toolbar = fragmentView.findViewById(R.id.lexicon_appbar);
        switch_button = fragmentView.findViewById(R.id.lexicon_switch_collapsed);
        expand_button = fragmentView.findViewById(R.id.lexicon_expand_button);
        dropDown_icon = fragmentView.findViewById(R.id.lexicon_dropdown_icon);
        search_clear_button = fragmentView.findViewById(R.id.lexicon_search_clear);
        from_lang_short = fragmentView.findViewById(R.id.lexicon_from_short);
        to_lang_short = fragmentView.findViewById(R.id.lexicon_to_short);

        // Lower the dropdown menu so that the currently selected item can be seen
        toLanguageSpinner.setDropDownVerticalOffset(100);
        fromLanguageSpinner.setDropDownVerticalOffset(100);

        search_clear_button.setVisibility(View.GONE);

        // Adapter for list items to show in recycler view
        ArrayAdapter<Language> spinnerLanguages = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, lexiconVM.getAvailableLanguages());
        spinnerLanguages.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        fromLanguageSpinner.setAdapter(spinnerLanguages);
        toLanguageSpinner.setAdapter(spinnerLanguages);


        // Set initial languages in spinners and text
        updateVM = false;
        fromLanguageSpinner.setSelection(lexiconVM.getLanguageIndex(lexiconVM.getSourceLanguage()));
        toLanguageSpinner.setSelection(lexiconVM.getLanguageIndex(lexiconVM.getTargetLanguage()));
        updateVM = true;
        from_lang_short.setText(parseLangCode(lexiconVM.getSourceLanguage().getLangCode()));
        to_lang_short.setText(parseLangCode(lexiconVM.getTargetLanguage().getLangCode()));

        // Listeners for views
        search_clear_button.setOnClickListener((v) -> {
            search_bar.getText().clear();
            searchWord(""); // Should reset the recyclerview to initial state
        });

        switch_button.setOnClickListener((v) -> {
            switch_button.animate().rotationBy(180).setDuration(200).start();
            switchLanguages();
            // Disable for 1 second after pressing to avoid spam-clicking
            switch_button.setEnabled(false);

            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            switch_button.setEnabled(true);
                        }
                    });
                }
            }, 1000);
        });

        expand_button.setOnClickListener((v) -> {
            if (appBarExpanded) {
                lexicon_toolbar.setExpanded(false);
            } else {
                lexicon_toolbar.setExpanded(true);
            }
        });

        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    search_bar.clearFocus();
                    hideKeyboard(textView);
                }
                searchString = search_bar.getText().toString();
                searchWord(searchString);
                return false;
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    search_clear_button.setVisibility(View.VISIBLE);
                } else {
                    search_clear_button.setVisibility(View.GONE);
                }
            }
        });

        fromLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (updateVM) {
                    Language lang = (Language) parent.getSelectedItem();
                    lexiconVM.setSourceLanguage(lang);

                    from_lang_short.setText(parseLangCode(lang.getLangCode()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update view model only when the user is manually selecting a language, not in initialization or language switching
                if (updateVM) {
                    Language lang = (Language) parent.getSelectedItem();
                    lexiconVM.setTargetLanguage(lang);
                    to_lang_short.setText(parseLangCode(lang.getLangCode()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initiate recycler view, set adapter
        NavController navController = Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment));
        rvLexicon = (RecyclerView) fragmentView.findViewById(R.id.lexicon_recyclerview);
        wordAdapter = new LexiconWordAdapter(lexiconDetailsFragment, navController);
        rvLexicon.setAdapter(wordAdapter);
        rvLexicon.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLexicon.addItemDecoration(new DividerItemDecoration((rvLexicon.getContext()), DividerItemDecoration.VERTICAL));

        return fragmentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Observe livedata from viwemodel
        //wordAdapter.setLexiconWordList(lexiconVM.getLexiconWords());

        lexiconVM.getWnExplanationsWithChecks().observe(getViewLifecycleOwner(), wnExplanations -> {
            List<LexiconWord> lexiconWordList = lexiconVM.getLexiconWords();
            for(WNExplanationWithCheck explanation : wnExplanations){
                for(int i = 0; i < lexiconWordList.size(); i++){
                    LexiconWord lexiconWord = lexiconWordList.get(i);
                    if(lexiconWord.getFunction().equals(explanation.getWnExplanation().getFunction())){
                        lexiconWord.setExplanation(explanation.getWnExplanation().getExplanation());
                        if(explanation.getCheckedFunction() != null) {
                            lexiconWord.setStatus(explanation.getCheckedFunction().getStatus());
                            lexiconWord.setLangcode(explanation.getCheckedFunction().getLangcode());
                        }
                        lexiconWordList.set(i, lexiconWord);
                        wordAdapter.setLexiconWordList(lexiconWordList);
                        if(!(lexiconWord.getSynonymCode().equals("random_siffra"))){
                            lexiconWord.setSynonymCode(explanation.getWnExplanation().getSynonym());
                            lexiconVM.getSynonyms().add(explanation.getWnExplanation().getSynonym());
                        }
                    }
                }
            }
            lexiconVM.setLexiconWords(lexiconWordList);
            Collections.sort(lexiconWordList, new Comparator<LexiconWord>() {
                @Override
                public int compare(LexiconWord lhs, LexiconWord rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    if(lhs.getStatus() != null && rhs.getStatus() == null)
                        return -1;
                    if(lhs.getStatus() == null && rhs.getStatus() != null)
                        return 1;
                    if(lhs.getStatus() == null && rhs.getStatus() == null)
                        return 0;
                    if (lhs.getStatus().equals(rhs.getStatus()))
                        return 0;
                    if(lhs.getStatus().equals("checked"))
                        return -1;
                    if(rhs.getStatus().equals("checked"))
                        return 1;
                    return 0;
                }
            });
            wordAdapter.setLexiconWordList(lexiconWordList);
            wordAdapter.notifyDataSetChanged();
        });

        rvLexicon.addItemDecoration(new DividerItemDecoration((rvLexicon.getContext()), DividerItemDecoration.VERTICAL));

    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateRecycler(String searchString){
        lexiconVM.wordTranslator(searchString);
        //TODO: Should this be this way?
        //wordAdapter.setLexiconWordList(lexiconVM.getLexiconWords());
    }

    private void clearRecyclerView(){
        //TODO: Clear livedata?
    }

    /*
    * Handles animation functionality, translates the language abbreviations and switch button to new positions when the toolbar expands
    * Sets the expand button to face up or down depending on whether the toolbar is expanded or not
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        appBarExpanded = (verticalOffset == 0);
        appBarCollapsed = Math.abs(verticalOffset) >= lexicon_toolbar.getTotalScrollRange();
        float normalizedOffset = Math.abs(((float)verticalOffset)/lexicon_toolbar.getTotalScrollRange());

        if (appBarExpanded) {
            updateAnimationOffsets();
            translateView(from_lang_short, fromOffsetX, fromOffsetY, 1);
            translateView(to_lang_short, toOffsetX, toOffsetY, 1);
            translateView(switch_button, switchOffsetX, switchOffsetY, 1);

            fromLanguageSpinner.setVisibility(View.VISIBLE);
            toLanguageSpinner.setVisibility(View.VISIBLE);
            from_lang_short.setVisibility(View.INVISIBLE);
            to_lang_short.setVisibility(View.INVISIBLE);

            dropDown_icon.setRotation(180);
        } else if (appBarCollapsed) {
            updateAnimationOffsets();
            translateView(from_lang_short, fromOffsetX, fromOffsetY, 0);
            translateView(to_lang_short, toOffsetX, toOffsetY, 0);
            translateView(switch_button, switchOffsetX, switchOffsetY, 0);

            from_lang_short.setVisibility(View.VISIBLE);
            to_lang_short.setVisibility(View.VISIBLE);

            dropDown_icon.setRotation(0);
        } else {
            translateView(from_lang_short, fromOffsetX, fromOffsetY, 1 - normalizedOffset);
            translateView(to_lang_short, toOffsetX, toOffsetY, 1 - normalizedOffset);
            translateView(switch_button, switchOffsetX, switchOffsetY, 1 - normalizedOffset);

            fromLanguageSpinner.setVisibility(View.INVISIBLE);
            toLanguageSpinner.setVisibility(View.INVISIBLE);
            from_lang_short.setVisibility(View.VISIBLE);
            to_lang_short.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        lexicon_toolbar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        lexicon_toolbar.removeOnOffsetChangedListener(this);
    }

    private void translateView(View v, float offsetX, float offsetY, float progress) {
        v.animate().translationX(offsetX * progress).setDuration(0).start();
        v.animate().translationY(offsetY * progress).setDuration(0).start();
    }

    private void updateAnimationOffsets() {
        fromOffsetX = (fromLanguageSpinner.getX() + (float)fromLanguageSpinner.getWidth()/4 - (from_lang_short.getX() - from_lang_short.getTranslationX()));
        fromOffsetY = (fromLanguageSpinner.getY() + (float)fromLanguageSpinner.getHeight()/4 - (from_lang_short.getY() - from_lang_short.getTranslationY()));

        toOffsetX = (toLanguageSpinner.getX() + (float)toLanguageSpinner.getWidth()/4 - (to_lang_short.getX() - to_lang_short.getTranslationX()));
        toOffsetY = (toLanguageSpinner.getY() + (float)toLanguageSpinner.getHeight()/4 - (to_lang_short.getY() - to_lang_short.getTranslationY()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        switchOffsetX = ((float)width/2 - (float)switch_button.getWidth()/2 - (switch_button.getX() - switch_button.getTranslationX()));
        switchOffsetY = (fromLanguageSpinner.getY() + (float)fromLanguageSpinner.getHeight()/5 - (switch_button.getY() - switch_button.getTranslationY()));
    }

    /*
     * Returns: the last 2 letters of the lang code which contains the 2 letter abbreviation of each language
     */
    private String parseLangCode(String langCode) {
        String firstLetters = langCode.substring(0, 3);
        Log.d(TAG, "First letters: " + firstLetters);
        if (firstLetters.contains("-"))
            return firstLetters.substring(0, 2).toUpperCase();
        else
            return langCode.substring(langCode.length() - 2).toUpperCase();
    }

    private void searchWord(String searchString) {
        clearRecyclerView();
        //wordAdapter.notifyItemRangeRemoved(0,listSize);
        if (!searchString.isEmpty()) {
            updateRecycler(searchString);
            //wordAdapter.notifyDataSetChanged();
        }
    }

    private void switchLanguages() {
        lexiconVM.switchLanguages();
        updateVM = false;
        fromLanguageSpinner.setSelection(lexiconVM.getLanguageIndex(lexiconVM.getSourceLanguage()));
        toLanguageSpinner.setSelection(lexiconVM.getLanguageIndex(lexiconVM.getTargetLanguage()));
        updateVM = true;
    }
}

