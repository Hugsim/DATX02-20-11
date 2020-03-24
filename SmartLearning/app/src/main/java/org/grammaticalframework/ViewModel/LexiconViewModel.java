package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.gf.Word;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;
    private SmartLearning sl;
    private Concr sourceLanguage;
    private Concr targetLanguage;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;
    private PGF gr;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (SmartLearning) getApplication().getApplicationContext();
        sourceLanguage = sl.getSourceConcr();
        targetLanguage = sl.getTargetConcr();
        gfClass = new GF(sl);
        gr = sl.getGrammar();
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

    public String speechTag(String lemma){
        Expr e = Expr.readExpr("MkTag (Inflection" + wordClass(lemma) + " " + lemma + ")");
        return targetLanguage.linearize(e);
    }

    public String inflect(String lemma){
        Expr e = Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection" + wordClass(lemma) + " " + lemma + ") \"\"");
        return targetLanguage.linearize(e);
    }

    public String wordClass(String lemma){
        return gr.getFunctionType(lemma).getCategory();
    }

    public void switchLanguages() {
        sl.switchLanguages();
        updateSourceLanguage();
        updateTargetLanguage();
    }

    private void updateSourceLanguage() {
        sourceLanguage = sl.getSourceConcr();
    }

    private void updateTargetLanguage() {
        targetLanguage = sl.getTargetConcr();
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