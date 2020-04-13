package org.grammaticalframework.ViewModel;

import android.content.Context;
import android.util.Log;
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
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.View.Fragments.LexiconDetailsFragment;
import org.grammaticalframework.View.Fragments.MainLexiconFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class LexiconWordAdapter extends RecyclerView.Adapter<LexiconWordAdapter.WordItemViewHolder> {

    private List<LexiconWord> lexiconWordList;
    private BaseFragment ldFragment;
    private NavController navController;
    private static final String TAG = LexiconWordAdapter.class.getSimpleName();

    public LexiconWordAdapter(BaseFragment ldFragment, NavController navController) {
        this.ldFragment = ldFragment;
        this.navController = navController;
        lexiconWordList = new ArrayList<>();
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

        LexiconWord lexiconWord = lexiconWordList.get(position);

        // Skickar med ordet (lemma + translatedWord) till LexiconDetailsFragment
        MainLexiconFragmentDirections.ActionLexiconFragmentToLexiconDetailsFragment action = MainLexiconFragmentDirections.actionLexiconFragmentToLexiconDetailsFragment(lexiconWord);
        action.setMessage(lexiconWord);
        action.setMessage2(lexiconWord.getLemma());

        viewHolder.itemView.setOnClickListener((v) -> {
            navController.navigate(action);
        });
        //viewHolder.wordTextView.setText(lexiconWord.getWord());
        viewHolder.wordTextView.setText(lexiconWord.getWord());
        viewHolder.explanationTextView.setText(lexiconWord.getExplanation());
        viewHolder.tagTextView.setText(lexiconWord.getTag());
        if(lexiconWord.getStatus() != null && lexiconWord.getStatus().equals("checked")){
            viewHolder.functionChecked.setText("✔️");
        } else {
            viewHolder.functionChecked.setText("❓");
        }
    }

    @Override
    public int getItemCount() {
        return lexiconWordList.size();
    }

    public void setLexiconWordList(List<LexiconWord> lexiconWordList) {
        this.lexiconWordList = lexiconWordList;
        notifyDataSetChanged(); //TODO: change this, not optimal
    }

    public class WordItemViewHolder extends RecyclerView.ViewHolder {

        public TextView wordTextView;
        public TextView explanationTextView;
        public TextView tagTextView;
        public TextView functionChecked;

        public WordItemViewHolder(View itemView) {
            super(itemView);

            wordTextView = itemView.findViewById(R.id.lexicon_item_name);
            explanationTextView = itemView.findViewById(R.id.lexicon_item_explanation);
            tagTextView = itemView.findViewById(R.id.lexicon_tag);
            functionChecked = itemView.findViewById(R.id.functionChecked);
        }
    }
}
