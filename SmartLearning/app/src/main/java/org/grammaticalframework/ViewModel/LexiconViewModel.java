package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import org.grammaticalframework.Language;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.Repository.WNExplanationRepository;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.gf.Word;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;

    private MutableLiveData<List<LexiconWord>> lexiconWordsLiveData = new MutableLiveData<>();

    private LiveData<List<WNExplanation>> wnExplanations = new MutableLiveData<>();


    private SmartLearning sl;
    private Concr sourceLanguage;
    private Concr targetLanguage;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;
    private PGF gr;

    //The functions that we are going to find in wordnet
    private List<String> functions = new ArrayList<>();

    //The repository for explanataions
    private WNExplanationRepository wnExplanationRepository;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (SmartLearning) getApplication().getApplicationContext();
        sourceLanguage = sl.getSourceConcr();
        targetLanguage = sl.getTargetConcr();
        gfClass = new GF(sl);
        gr = sl.getGrammar();
        wnExplanationRepository = new WNExplanationRepository(application);
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

        for (MorphoAnalysis an : sourceLanguage.lookupMorpho(word)) {
            if (targetLanguage.hasLinearization(an.getLemma())) {
                Expr e = Expr.readExpr(an.getLemma());
                String function = e.unApp().getFunction();
                for (String s : targetLanguage.linearizeAll(e)) {
                    if (!translatedWords.contains(s)) {
                        functions.add(function);
                        translatedWords.add(s);
                        //WNExplanation wne = wnExplanationRepository.getWNExplanationSync(function);
                        lexiconWords.add(new LexiconWord(an.getLemma(), s, "", speechTag(an.getLemma()), function));
                    }
                }
            }
        }
        /*lexiconWordsLiveData = (MutableLiveData<List<LexiconWord>>) Transformations.switchMap(wnExplanationRepository.getWNExplanations(functions), explanations -> {
            Log.d(TAG, "inside of Transformations");
            for(LexiconWord lw : lexiconWords){
                for(WNExplanation we : explanations) {
                    if(lw.getFunction().equals(we.getFunction())) {
                        lw.setExplanation(we.getExplanation());
                        List<LexiconWord> list = lexiconWordsLiveData.getValue();
                        list.add(lw);
                        lexiconWordsLiveData.setValue(list);
                        return lexiconWordsLiveData;
                    }
                }
            }
            return lexiconWordsLiveData;
        });*/
        lexiconWordsLiveData.setValue(lexiconWords);
        wnExplanations = wnExplanationRepository.getWNExplanations(functions);
    }

    public LiveData<List<LexiconWord>> getLexiconWords() {
        return lexiconWordsLiveData;
    }

    public LiveData<List<WNExplanation>> getWNExplanations() {
        return wnExplanations;
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