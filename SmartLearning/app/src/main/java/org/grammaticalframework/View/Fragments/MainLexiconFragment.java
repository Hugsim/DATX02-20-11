package org.grammaticalframework.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.grammaticalframework.R;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.View.FragmentFactory;
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.ViewModel.LexiconWordAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainLexiconFragment extends BaseFragment {
    private LexiconViewModel lexiconVM;
    private List<LexiconWord> lexiconWordList;
    private final BaseFragment lexiconDetailsFragment = FragmentFactory.createLexiconDetailsFragment();
    private EditText search_bar;
    private String searchString;
    private RecyclerView rvLexicon;
    private LexiconWordAdapter wordAdapter;
    private int listSize;

    LexiconViewModel model;

    final static String TAG = MainLexiconFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lexiconVM = new ViewModelProvider(requireActivity()).get(LexiconViewModel.class);

        View fragmentView = inflater.inflate(R.layout.fragment_lexicon, container, false);
        SmartLearning sl = (SmartLearning) getActivity().getApplicationContext();
        search_bar = fragmentView.findViewById(R.id.lexicon_searchbar);
        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    search_bar.clearFocus();
                    hideKeyboard(textView);
                }
                searchString = search_bar.getText().toString();
                System.out.println(search_bar.getText().toString());
                clearRecyclerView();
                wordAdapter.notifyItemRangeRemoved(0,listSize);
                updateRecycler(searchString);
                wordAdapter.notifyDataSetChanged();
                return false;
            }
        });
        rvLexicon = (RecyclerView) fragmentView.findViewById(R.id.lexicon_recyclerview);
        updateRecycler("fish");
        wordAdapter = new LexiconWordAdapter(lexiconWordList, lexiconDetailsFragment);
        rvLexicon.setAdapter(wordAdapter);
        rvLexicon.setLayoutManager(new LinearLayoutManager(getActivity()));

        return fragmentView;

    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateRecycler(String searchString){
        lexiconVM.wordTranslator(searchString);
        lexiconWordList = lexiconVM.getTranslatedWords();
    }

    private void clearRecyclerView(){
        int size = lexiconVM.getTranslatedWords().size();
        lexiconVM.getTranslatedWords().clear();
        listSize = size;
    }
}

