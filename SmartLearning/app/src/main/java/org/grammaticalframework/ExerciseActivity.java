package org.grammaticalframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

    }

    public void buttonClick(View view) {
        int id = view.getId();
        Toast toast;

        switch (id){
            case R.id.grammar_button:
                // Intent intent = new Intent(this, GrammarActivity.class);
                toast = Toast.makeText(this, "Go to grammar", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.vocabulary_button:
                // Intent intent = new Intent(this, WordsActivity.class);
                toast = Toast.makeText(this, "Go to vocabulary", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        // startActivity(intent);

    }
}
