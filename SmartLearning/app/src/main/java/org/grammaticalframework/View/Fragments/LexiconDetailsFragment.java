package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
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
import org.grammaticalframework.ViewModel.LexiconViewModel;
import org.grammaticalframework.ViewModel.LexiconWord;


public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private String translatedWord;
    private String lemma;
    private NavController navController;
    private LexiconViewModel model;
    private WebView webView;
    private TextView textView1;
    private static final String TAG = LexiconDetailsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);
        textView1 = fragmentView.findViewById(R.id.translated_word);
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
            LexiconDetailsFragmentArgs args = LexiconDetailsFragmentArgs.fromBundle(getArguments());
            LexiconWord word = args.getMessage();
            translatedWord = word.getWord();
            lemma = word.getLemma();
            textView1.setText(translatedWord);
            // TODO: maybe perhaps not write html like this?
            String html = "<p>The function for this word is: " + word.getFunction() + "</p>";
            html += "<h2>Explanation</h2>";
            html += "<p>" + word.getExplanation() +"</p>";
            html += model.inflect(lemma);
            webView.loadData(html, "text/html", "UTF-8");
        }
    }
}
