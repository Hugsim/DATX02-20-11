package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.Grammarlex;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {
    private Grammarlex sl;

    public List<String> getTriviaList() {
        return triviaList;
    }

    private List<String> triviaList;

    public HomeViewModel(@NonNull Application application){
        super(application);
        sl = (Grammarlex) getApplication().getApplicationContext();
        triviaList = new ArrayList<>();
    }

    public List<Language> getAvailableLanguages() {
        return sl.getAvailableLanguages();
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

    public int getLanguageIndex(Language lang) {
        return sl.getLanguageIndex(lang);
    }

    public void fillTriviaList(){
        triviaList.add("This application is created by Chalmers students for their bachelor thesis!");
        triviaList.add("The lexicon tells you if the translation of a word is confident or not!");
        triviaList.add("43% of the world's population are considered bilingual!");
        triviaList.add("13% of the world's population are considered trilingual!");
        triviaList.add("If available, the lexicon gives an explanation, synonyms and inflections for the word!");
    }
}
