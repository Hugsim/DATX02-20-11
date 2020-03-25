package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
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
    private Language source;
    private Language target;

    private SmartLearning sl;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;
    private PGF gr;

    //The functions that we are going to find in wordnet
    private List<String> functions = new ArrayList<>();

    //The repository for explanataions

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (SmartLearning) getApplication().getApplicationContext();
        gfClass = new GF(sl);
        gr = sl.getGrammar();
    }

    public void wordTranslator(String word) {
        if (!lexiconWords.isEmpty()) {
            lexiconWords.clear();
        }
        if (!translatedWords.isEmpty()) {
            translatedWords.clear();
        }
        if (!functions.isEmpty()){
            functions.clear();
        }

        // Load language (first time after switching) before translating to avoid delay in switching language
        sl.setSourceLanguage(source);
        sl.setTargetLanguage(target);

        for (MorphoAnalysis an : sl.getSourceConcr().lookupMorpho(word)) {
            if (sl.getTargetConcr().hasLinearization(an.getLemma())) {
                Expr e = Expr.readExpr(an.getLemma());
                String function = e.unApp().getFunction();
                for (String s : sl.getTargetConcr().linearizeAll(e)) {
                    if (!translatedWords.contains(s)) {
                        functions.add(function);
                        translatedWords.add(s);
                        lexiconWords.add(new LexiconWord(an.getLemma(), s, "", speechTag(an.getLemma())));
                    }
                }
            }
        }
    }

    public String speechTag(String lemma){
        Expr e = Expr.readExpr("MkTag (Inflection" + wordClass(lemma) + " " + lemma + ")");
        return sl.getTargetConcr().linearize(e);
    }

    public String inflect(String lemma){
        Expr e = Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection" + wordClass(lemma) + " " + lemma + ") \"\"");
        return sl.getTargetConcr().linearize(e);
    }

    public String wordClass(String lemma){
        return gr.getFunctionType(lemma).getCategory();
    }

    public void switchLanguages() {
        sl.switchLanguages();
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
        source = lang;
    }

    public void setTargetLanguage(Language lang) {
        target = lang;
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }
}