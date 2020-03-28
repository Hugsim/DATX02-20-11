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
    private Concr sourceLanguage;
    private Concr targetLanguage;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        gfClass = new GF((SmartLearning) getApplication().getApplicationContext());
        sourceLanguage = gfClass.getSourceConcr();
        targetLanguage = gfClass.getTargetConcr();
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

    public void wordTranslator(String word) {
        if (!lexiconWords.isEmpty()) {
            lexiconWords.clear();
        }
        if (!translatedWords.isEmpty()) {
            translatedWords.clear();
        }

        for (MorphoAnalysis an : sourceLanguage.lookupMorpho(word)) {
            if (targetLanguage.hasLinearization(an.getLemma())) {
                Expr e = Expr.readExpr(an.getLemma());
                for (String s : targetLanguage.linearizeAll(e)) {
                    if (!translatedWords.contains(s)) {
                        translatedWords.add(s);
                        lexiconWords.add(new LexiconWord(an.getLemma(), s, "explanation", speechTag(an.getLemma())));
                    }
                }
            }
        }
    }

    //gfClass.partOfSpeech(new Word(an.getLemma())))

    public String speechTag(String lemma) {
        return gfClass.partOfSpeech(lemma);
    }

    public String inflect(String lemma) {
        return gfClass.generateInflectionTable(lemma);
    }

    public String wordClass(String lemma) {
        return gfClass.typeOfFunction(lemma);
    }

    public void switchLanguages() {
        gfClass.getSmartLearningInstance().switchLanguages();
        updateSourceLanguage();
        updateTargetLanguage();
    }

    private void updateSourceLanguage() {
        sourceLanguage = gfClass.getSourceConcr();
    }

    private void updateTargetLanguage() {
        targetLanguage = gfClass.getTargetConcr();
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
        updateSourceLanguage();
    }

    public void setTargetLanguage(Language lang) {
        gfClass.getSmartLearningInstance().setTargetLanguage(lang);
        updateTargetLanguage();
    }
}