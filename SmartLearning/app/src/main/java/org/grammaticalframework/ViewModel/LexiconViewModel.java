package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;
    private SmartLearning sl;
    private Concr fromLanguage;
    private Concr toLanguage;

    public LexiconViewModel(@NonNull Application application) {
        super(application);

        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (SmartLearning) getApplication().getApplicationContext();
        fromLanguage = sl.getSourceConcr();
        toLanguage = sl.getTargetConcr();
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

   public void wordTranslator(String word){
        if (!translatedWords.isEmpty()){
            translatedWords.clear();
        }
        for (MorphoAnalysis an : fromLanguage.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            for (String s : toLanguage.linearizeAll(e)) {
                if (!translatedWords.contains(s)){
                translatedWords.add(s);
                }
                Log.d(TAG, s);
            }
        }
        stringToLexicon();
    }

    private void stringToLexicon(){
        for (String string: translatedWords){
            lexiconWords.add(new LexiconWord(string,"explanation"));
        }
    }

    public void switchLanguages() {
        sl.switchLanguages();
        updateSourceLanguage();
        updateTargetLanguage();
    }

    private void updateSourceLanguage() {
        fromLanguage = sl.getSourceConcr();
    }

    private void updateTargetLanguage() {
        toLanguage = sl.getTargetConcr();
    }

    public List<Language> getAvailableLanguages() {
        return sl.getAvailableLanguages();
    }

    public int getLanguageIndex(Language lang) {
        return sl.getLanguageIndex(lang);
    }

    public Language getSourceLanguage() {
        return sl.getSourceLanguage();
    }

    public Language getTargetLanguage(){
        return sl.getTargetLanguage();
    }

    public void setSourceLanguage(Language lang) {
        sl.setSourceLanguage(lang);
        updateSourceLanguage();
    }

    public void setTargetLanguage(Language lang) {
        sl.setTargetLanguage(lang);
        updateTargetLanguage();
    }
}