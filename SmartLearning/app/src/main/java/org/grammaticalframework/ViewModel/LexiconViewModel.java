package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import org.grammaticalframework.Language;
import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.Repository.WNExplanationRepository;
import org.grammaticalframework.Repository.WNExplanationWithCheck;
import org.grammaticalframework.Grammarlex;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;

    //static private LiveData<List<WNExplanation>> wnExplanations;
    static private LiveData<List<WNExplanationWithCheck>> wnExplanationsWithChecks;
    static private LiveData<List<WNExplanation>> wnSynonyms;

    //functions both used for finding explanations
    //private MutableLiveData<List<String>> functionsToSearchFor = new MutableLiveData<>();
    private MutableLiveData<Pair<List<String>,String>> functionsToSearchForWithLangcode = new MutableLiveData<>();
    private MutableLiveData<List<String>> synonymsToSearchFor = new MutableLiveData<>();

    private Grammarlex sl;

    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private GF gfClass;

    //The functions that we are going to find in wordnet
    private List<String> functions = new ArrayList<>();
    private List<String> synonyms = new ArrayList<>();

    //The repository for explanataions
    private WNExplanationRepository wnExplanationRepository;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (Grammarlex) getApplication().getApplicationContext();
        gfClass = new GF(sl);
        wnExplanationRepository = new WNExplanationRepository(application);

        /*wnExplanations = Transformations.switchMap(functionsToSearchFor, functions -> {
           return wnExplanationRepository.getWNExplanations(functions);
        });*/

        wnExplanationsWithChecks = Transformations.switchMap(functionsToSearchForWithLangcode, pair -> {
            return wnExplanationRepository.getWNExplanationsWithCheck(pair.first, pair.second);
        });

        wnSynonyms = Transformations.switchMap(synonymsToSearchFor, synonyms -> {
            return wnExplanationRepository.getSynonyms(synonyms);
        });

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

        if(!synonyms.isEmpty()){
            synonyms.clear();
        }

        for (MorphoAnalysis an : sl.getSourceConcr().lookupMorpho(word)) {
            if (sl.getTargetConcr().hasLinearization(an.getLemma())) {
                Expr e = Expr.readExpr(an.getLemma());
                String function = e.unApp().getFunction();
                for (String s : sl.getTargetConcr().linearizeAll(e)) {
                    if (!translatedWords.contains(s)) {
                        functions.add(function);
                        translatedWords.add(s);
                        lexiconWords.add(new LexiconWord(an.getLemma(), s, "", speechTag(an.getLemma()), function, "", ""));
                    }
                }
            }
        }

        //Needs to be called, updates explanation livedata
       // searchForFunctions(functions);
        searchForSynonyms(synonyms);
        searchForFunctionsWithCheck(functions);
    }



    /*private void searchForFunctions(List<String> functions){
        functionsToSearchFor.setValue(functions);
    }*/

    private void searchForSynonyms(List<String> synonyms){
        synonymsToSearchFor.setValue(synonyms);
    }

    private void searchForFunctionsWithCheck(List<String> functions){
        functionsToSearchForWithLangcode.setValue(new Pair<>(functions,sl.getTargetLanguage().getLangCode()));
    }

    public List<LexiconWord> getLexiconWords() {
        return lexiconWords;
    }

    public List<String> getSynonyms(){
        return synonyms;
    }

    public void setLexiconWords(List<LexiconWord> lexiconWords){this.lexiconWords = lexiconWords;}

    /*public LiveData<List<WNExplanation>> getWNExplanations() {
        return wnExplanations;
    }*/

    public LiveData<List<WNExplanation>> getWNSynonyms() {
        return wnSynonyms;
    }

    public LiveData<List<WNExplanationWithCheck>> getWnExplanationsWithChecks() {
        return wnExplanationsWithChecks;
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
        return sl.getGrammar().getFunctionType(lemma).getCategory();
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
        sl.setSourceLanguage(lang);
    }

    public void setTargetLanguage(Language lang) {
        sl.setTargetLanguage(lang);
    }

    public Concr getTargetConcr() { return sl.getTargetConcr();}

}