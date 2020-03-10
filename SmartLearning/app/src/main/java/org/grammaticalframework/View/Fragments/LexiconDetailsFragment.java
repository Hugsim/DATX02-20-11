package org.grammaticalframework.View.Fragments;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.LexiconDetailsAdapter;
import org.grammaticalframework.ViewModel.LexiconDetailsViewModel;

import java.util.ArrayList;
import java.util.List;


public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private String translatedWord;
    private String lemma;
    private NavController navController;
    private LexiconDetailsViewModel model;
    private WebView webView;
    private List<String> inflections;
    private RecyclerView recycler;
    private LexiconDetailsAdapter mAdapter;
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
            navController.navigate(R.id.action_lexiconDetailsFragment_to_lexiconFragment);
        });

        model = new ViewModelProvider(requireActivity()).get(LexiconDetailsViewModel.class);
/*
        inflections = model.getInflections();
        recycler = (RecyclerView) fragmentView.findViewById(R.id.lexicon_details_recyclerview);
        mAdapter = new LexiconDetailsAdapter(inflections);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


 */
        return fragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            LexiconDetailsFragmentArgs args = LexiconDetailsFragmentArgs.fromBundle(getArguments());
            translatedWord = args.getMessage();
            lemma = args.getMessage2();
            //model.inflect(translatedWord);
            textView1.setText(translatedWord);
            String html = model.inflect(lemma);

            webView.loadData(html, "text/html", "UTF-8");
        }
    }
}
