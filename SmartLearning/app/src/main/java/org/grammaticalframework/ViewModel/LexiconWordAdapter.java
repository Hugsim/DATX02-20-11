package org.grammaticalframework.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.grammaticalframework.R;
import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.View.Fragments.LexiconDetailsFragment;
import org.grammaticalframework.View.Fragments.MainLexiconFragmentDirections;

import java.util.List;

public class LexiconWordAdapter extends RecyclerView.Adapter<LexiconWordAdapter.WordItemViewHolder> {

    private List<LexiconWord> lexiconWordList;
    private BaseFragment ldFragment;
    private NavController navController;
    static private String savedString;

    static public void setSavedString(String string){
        savedString = string;
    }

    static public String getSavedString(){
        return  savedString;
    }

    public LexiconWordAdapter(List<LexiconWord> word_list, BaseFragment ldFragment, NavController navController) {
        lexiconWordList = word_list;
        this.ldFragment = ldFragment;
        this.navController = navController;
    }

    @Override
    public LexiconWordAdapter.WordItemViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        final View wordItemView = inflater.inflate(R.layout.lexicon_recycleritem, parent, false);
        WordItemViewHolder viewHolder = new WordItemViewHolder(wordItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LexiconWordAdapter.WordItemViewHolder viewHolder, int position) {
        // Tar bort ordklassen från ordet
        LexiconWord lexiconWord = lexiconWordList.get(position);
        String[] words = lexiconWord.getWord().split(" ");

        StringBuilder sb = new StringBuilder(words[1]);
        for(int i = 2; i < words.length; i++){
            sb.append(" ");
            sb.append(words[i]);
        }

        String word = sb.toString();

        // Skickar med ordet till LexiconDetailsFragment
        MainLexiconFragmentDirections.ActionLexiconFragmentToLexiconDetailsFragment action = MainLexiconFragmentDirections.actionLexiconFragmentToLexiconDetailsFragment();
        action.setMessage(lexiconWord.getWord());

        viewHolder.itemView.setOnClickListener((v) -> {
            navController.navigate(action);
        });
        //viewHolder.wordTextView.setText(lexiconWord.getWord());
        viewHolder.wordTextView.setText(word);
        viewHolder.explanationTextView.setText(lexiconWord.getExplanation());
    }

    @Override
    public int getItemCount() {
        return lexiconWordList.size();
    }

    public class WordItemViewHolder extends RecyclerView.ViewHolder {

        public TextView wordTextView;
        public TextView explanationTextView;

        public WordItemViewHolder(View itemView) {
            super(itemView);

            wordTextView = itemView.findViewById(R.id.lexicon_item_name);
            explanationTextView = itemView.findViewById(R.id.lexicon_item_explanation);
        }
    }
}
