package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        gfClass = new GF((SmartLearning) getApplication().getApplicationContext());
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

    public void wordTranslator(String word) {
        lexiconWords.clear();
        translatedWords.clear();

        for (String an : gfClass.possibleFunctions(word)) {
            if (gfClass.hasLinearization(an)) {
                Expr e = GF.readExpr(an);
                for (String s : gfClass.allLinearizations(e)) {
                    if (!translatedWords.contains(s)) {
                        translatedWords.add(s);
                        lexiconWords.add(new LexiconWord(an, s, "explanation", gfClass.partOfSpeech(an)));
                    }
                }
            }
        }
    }

    public String inflect(String lemma) {
        return gfClass.generateInflectionTable(lemma);
    }

    public void switchLanguages() {
        gfClass.getSmartLearningInstance().switchLanguages();
    }

    public List<Language> getAvailableLanguages() {
        return gfClass.getSmartLearningInstance().getAvailableLanguages();
    }

    public int getLanguageIndex(Language lang) {
        return gfClass.getSmartLearningInstance().getLanguageIndex(lang);
    }

    public Language getSourceLanguage() {
        return gfClass.getSourceLang();
    }

    public Language getTargetLanguage(){
        return gfClass.getTargetLang();
    }

    public void setSourceLanguage(Language lang) {
        gfClass.getSmartLearningInstance().setSourceLanguage(lang);
    }

    public void setTargetLanguage(Language lang) {
        gfClass.getSmartLearningInstance().setTargetLanguage(lang);
    }
}