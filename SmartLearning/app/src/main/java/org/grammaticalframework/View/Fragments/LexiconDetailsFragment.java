package org.grammaticalframework.View.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.grammaticalframework.R;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;

import java.util.ArrayList;
import java.util.List;

import org.grammaticalframework.gf.GF;


public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private Boolean hasSynonyms;
    private Boolean hasNextSynonym;
    private List <String> foundSynonymList;
    private String translatedWord;
    private String lemma;
    private NavController navController;
    private LexiconViewModel model;
    private WebView webView;
    private TextView wordView;
    private TextView debugFunctionTextView;
    private TextView explanationTextView;
    private TextView synonymTextView;
    private TextView explanationHeader;
    private TextView synonymsHeader;
    private TextView inflectionsHeader;
    private static final String TAG = LexiconDetailsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);

        foundSynonymList = new ArrayList<>();
        wordView = fragmentView.findViewById(R.id.translated_word);
        debugFunctionTextView = fragmentView.findViewById(R.id.debugFunctionTextView);
        explanationTextView = fragmentView.findViewById(R.id.explanationTextView);
        synonymTextView = fragmentView.findViewById(R.id.synonymTextView);
        webView = (WebView) fragmentView.findViewById(R.id.web_view);
        explanationHeader = fragmentView.findViewById(R.id.explanationHeader);
        synonymsHeader = fragmentView.findViewById(R.id.synonymsHeader);
        inflectionsHeader = fragmentView.findViewById(R.id.inflectionsHeader);

        hasSynonyms= false;
        hasNextSynonym = false;

        navController = Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment));
        backButton = fragmentView.findViewById(R.id.lexicon_details_back);

        backButton.setOnClickListener((v) -> {
            navController.popBackStack();
        });

        model = new ViewModelProvider(getActivity()).get(LexiconViewModel.class);
        return fragmentView;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
            wordView.setText(translatedWord);
            debugFunctionSB.append("The function for this word is: ").append(word.getFunction());
            explanationSB.append(word.getExplanation());
            debugFunctionTextView.setText(debugFunctionSB);
            explanationTextView.setText(explanationSB);
            loadSynonymWordsForOneWord(word);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                explanationHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.explanation_colorstate));
                synonymsHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.synonyms_colorstate));
                inflectionsHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.inflections_colorstate));
            }

            // TODO: maybe perhaps not write html like this?
            String html = model.inflect(lemma);
            webView.loadData(html, "text/html", "UTF-8");
            webView.setBackgroundResource(R.drawable.content_background);
            webView.setBackgroundColor(Color.TRANSPARENT);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView web, String url) {
                    web.loadUrl("javascript:(function(){ document.body.style.padding = '10px'})();");
                }
            });
        }
    }

    private void loadSynonymWordsForOneWord(LexiconWord lexiconWord){
        StringBuilder synonymSB = new StringBuilder();

        model.getWNSynonyms().observe(getViewLifecycleOwner(), wnSynonyms ->{
            for (WNExplanation synonym : wnSynonyms){
                if(!synonym.getSynonym().equals("random_siffra")){
                        if (!lexiconWord.getSynonymCode().equals("random_siffra") && lexiconWord.getExplanation().equals(synonym.getExplanation())
                                && !foundSynonymList.contains(synonym.getFunction()) && !(lexiconWord.getFunction().equals(synonym.getFunction()))){
                            foundSynonymList.add(synonym.getFunction());
                            hasSynonyms = true;
                            //lexiconWord.setSynonymWords(constructSynonymWordsString(synonym.getFunction(), synonymSB));
                            String temp = synonym.getFunction();
                            constructSynonymWordsString(GF.linearizeFunction(temp, model.getTargetConcr()), synonymSB);
                            Log.d(TAG, "FOUND SYNONYMS");

                        }
                    if(hasSynonyms){
                        synonymTextView.setText(synonymSB);
                        synonymTextView.setVisibility(View.VISIBLE);
                        synonymsHeader.setVisibility(View.VISIBLE);
                        debugFunctionTextView.setVisibility(View.GONE);
                    } else {
                        synonymTextView.setVisibility(View.GONE);
                        synonymsHeader.setVisibility(View.GONE);
                        foundSynonymList.clear();
                        debugFunctionTextView.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    private void constructSynonymWordsString(String synonymCode, StringBuilder synonymSB){
        if(synonymSB.length() != 0) {
            synonymSB.append(", ");
        }
        synonymSB.append(synonymCode);
    }
}
