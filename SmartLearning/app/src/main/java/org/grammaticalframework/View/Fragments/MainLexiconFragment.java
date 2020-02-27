package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.grammaticalframework.R;
import org.grammaticalframework.View.FragmentFactory;
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.ViewModel.LexiconWordAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainLexiconFragment extends BaseFragment {

    private LexiconViewModel lexiconVM;
    private List<LexiconWord> lexiconWordList;
    private final BaseFragment lexiconDetailsFragment = FragmentFactory.createLexiconDetailsFragment();

    public MainLexiconFragment(String tag) {
        super(tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon, container, false);


        RecyclerView rvLexicon = (RecyclerView) fragmentView.findViewById(R.id.lexicon_recyclerview);

        lexiconWordList = testPopulateWords(6);

        LexiconWordAdapter wordAdapter = new LexiconWordAdapter(lexiconWordList, lexiconDetailsFragment);

        rvLexicon.setAdapter(wordAdapter);
        rvLexicon.setLayoutManager(new LinearLayoutManager(getActivity()));

        return fragmentView;

    }

    private List<LexiconWord> testPopulateWords(int amount) {
        List<LexiconWord> wordList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            wordList.add(new LexiconWord("Word " + (i + 1), "Explanation"));
        }

        return wordList;
    }
}
