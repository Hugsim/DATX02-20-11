package org.grammaticalframework.View.Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TableRow;
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
import org.grammaticalframework.pgf.Concr;


public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private List <String> foundSynonymList;
    private String translatedWord;
    private String lemma;
    private NavController navController;
    private LexiconViewModel model;
    private WebView webView;
    private TextView wordView;
    private TextView explanationTextView;
    private TextView synonymTextView;
    private TextView explanationHeader;
    private TextView synonymsHeader;
    private TextView inflectionsHeader;
    private TableRow synonymsRow;
    private static final String TAG = LexiconDetailsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);

        foundSynonymList = new ArrayList<>();
        wordView = fragmentView.findViewById(R.id.translated_word);
        explanationTextView = fragmentView.findViewById(R.id.explanationTextView);
        synonymTextView = fragmentView.findViewById(R.id.synonymTextView);
        webView = (WebView) fragmentView.findViewById(R.id.web_view);
        explanationHeader = fragmentView.findViewById(R.id.explanationHeader);
        synonymsHeader = fragmentView.findViewById(R.id.synonymsHeader);
        inflectionsHeader = fragmentView.findViewById(R.id.inflectionsHeader);
        synonymsRow = fragmentView.findViewById(R.id.synonymsRow);

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

        // Hide synonyms elements if no synonyms are found
        synonymTextView.setVisibility(View.GONE);
        synonymsHeader.setVisibility(View.GONE);
        synonymsRow.setVisibility(View.GONE);

        if(getArguments() != null){
            SpannableStringBuilder explanationSB = new SpannableStringBuilder();
            LexiconDetailsFragmentArgs args = LexiconDetailsFragmentArgs.fromBundle(getArguments());
            LexiconWord word = args.getMessage();
            translatedWord = word.getWord();
            lemma = word.getLemma();
            wordView.setText(translatedWord);
            if(word.getStatus() == null || !word.getStatus().equals("checked")) {
                explanationSB.append("This word has not been checked in its translation and may therefore be incorrect!");
                explanationSB.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 81, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                explanationSB.append(System.getProperty("line.separator"));
            }
            explanationSB.append(word.getExplanation());
            explanationTextView.setText(explanationSB);
            loadSynonymWordsForOneWord(word);

            // Set colors for each header in this view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                explanationHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.explanation_colorstate));
                synonymsHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.synonyms_colorstate));
                inflectionsHeader.setForegroundTintList(ContextCompat.getColorStateList(getContext(), R.color.inflections_colorstate));
            }

            // TODO: maybe perhaps not write html like this?
            String html = model.inflect(lemma);
            webView.loadData(html, "text/html", "UTF-8");

            // Web view background and padding has to be set programmatically
            webView.setBackgroundResource(R.drawable.content_background);
            webView.setBackgroundColor(Color.TRANSPARENT);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView web, String url) {
                    web.loadUrl("javascript:(function(){ document.body.style.padding = '10px';})();");
                    webView.getSettings().setJavaScriptEnabled(false);
                }
            });
        }
    }

    private void loadSynonymWordsForOneWord(LexiconWord lexiconWord){
        StringBuilder synonymSB = new StringBuilder();
        List<String> synonyms = new ArrayList<>();

        model.getWNSynonyms().observe(getViewLifecycleOwner(), wnSynonyms ->{
            Concr target = model.getTargetConcr();

            for (WNExplanation synonym : wnSynonyms){
                if(!synonym.getSynonym().equals("random_siffra")){
                    if (!lexiconWord.getSynonymCode().equals("random_siffra") && lexiconWord.getExplanation().equals(synonym.getExplanation())
                            && !foundSynonymList.contains(synonym.getFunction()) && !(lexiconWord.getFunction().equals(synonym.getFunction()))){
                        foundSynonymList.add(synonym.getFunction());
                        //lexiconWord.setSynonymWords(constructSynonymWordsString(synonym.getFunction(), synonymSB));
                        String func = synonym.getFunction();
                        if (target.hasLinearization(func)) {
                            String synonymWord = GF.linearizeFunction(func, model.getTargetConcr());
                            if (!synonyms.contains(synonymWord) && !synonymWord.equals(lexiconWord.getWord())) {
                                constructSynonymWordsString(synonymWord, synonymSB);
                                synonyms.add(synonymWord);
                            }
                        }
                    }
                }
            }
            if(synonymSB.length() != 0){
                synonymTextView.setText(synonymSB);
                synonymTextView.setVisibility(View.VISIBLE);
                synonymsHeader.setVisibility(View.VISIBLE);
                synonymsRow.setVisibility(View.VISIBLE);
            } else {
                foundSynonymList.clear();
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
