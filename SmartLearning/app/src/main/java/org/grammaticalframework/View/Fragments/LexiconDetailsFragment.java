package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.ViewModel.LexiconWordAdapter;

public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private String translatedWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);

        backButton = fragmentView.findViewById(R.id.lexicon_details_back);

        backButton.setOnClickListener((v) -> {
            AppCompatActivity activity = (AppCompatActivity) fragmentView.getContext();
            Fragment lexiconFragment = getFragmentManager().findFragmentByTag("lexiconFragment");
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, lexiconFragment).addToBackStack(null).commit();
        });

        return fragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            LexiconDetailsFragmentArgs args = LexiconDetailsFragmentArgs.fromBundle(getArguments());
            translatedWord = args.getMessage();
            TextView word = view.findViewById(R.id.lexicon_details_word);
            word.setText(translatedWord);
        }
    }
}
