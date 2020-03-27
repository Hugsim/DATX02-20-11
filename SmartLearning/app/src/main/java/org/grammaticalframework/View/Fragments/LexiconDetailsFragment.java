package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.grammaticalframework.R;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.List;


public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private List <String> foundSynonymList;
    private String translatedWord;
    private String lemma;
    private NavController navController;
    private LexiconViewModel model;
    private WebView webView;
    private TextView textView1;
    private TextView debugFunctionTextView;
    private TextView explanationTextView;
    private TextView synonymTextView;
    private static final String TAG = LexiconDetailsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);

        foundSynonymList = new ArrayList<>();
        textView1 = fragmentView.findViewById(R.id.translated_word);
        debugFunctionTextView = fragmentView.findViewById(R.id.debugFunctionTextView);
        explanationTextView = fragmentView.findViewById(R.id.explanationTextView);
        synonymTextView = fragmentView.findViewById(R.id.synonymTextView);
        webView = (WebView) fragmentView.findViewById(R.id.web_view);

        navController = Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment));
        backButton = fragmentView.findViewById(R.id.lexicon_details_back);

        backButton.setOnClickListener((v) -> {
            navController.popBackStack();
        });

        model = new ViewModelProvider(getActivity()).get(LexiconViewModel.class);
        return fragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            StringBuilder explanationSB = new StringBuilder();
            StringBuilder debugFunctionSB = new StringBuilder();
            LexiconDetailsFragmentArgs args = LexiconDetailsFragmentArgs.fromBundle(getArguments());
            LexiconWord word = args.getMessage();
            translatedWord = word.getWord();
            lemma = word.getLemma();
            textView1.setText(translatedWord);
            debugFunctionSB.append("The function for this word is: ").append(word.getFunction());
            explanationSB.append("Explanation: ").append(word.getExplanation());
            debugFunctionTextView.setText(debugFunctionSB);
            explanationTextView.setText(explanationSB);

            // TODO: maybe perhaps not write html like this?
            String html = model.inflect(lemma);
            webView.loadData(html, "text/html", "UTF-8");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model.getWNSynonyms().observe(getViewLifecycleOwner(), wnSynonyms ->{
            List<LexiconWord> lexiconWordList = model.getLexiconWords();
            for (WNExplanation synonym : wnSynonyms){
                for (int i = 0; i < lexiconWordList.size(); i++){
                    LexiconWord lexiconWord = lexiconWordList.get(i);
                    if (!lexiconWord.getSynonym().equals("random_siffra") && lexiconWord.getSynonym().equals(synonym.getSynonym())
                            && !foundSynonymList.contains(synonym.getFunction())){
                        foundSynonymList.add(synonym.getFunction());
                        Log.d(TAG, "FOUND SYNONYMS");
                    }
                }
            }
            StringBuilder synonymSB = new StringBuilder();
            synonymSB.append("Synonyms: ");
            for (String synonyms : foundSynonymList){
                synonymSB.append(synonyms).append(",").append(" ");
            }
            synonymTextView.setText(synonymSB);
        });
    }

}
