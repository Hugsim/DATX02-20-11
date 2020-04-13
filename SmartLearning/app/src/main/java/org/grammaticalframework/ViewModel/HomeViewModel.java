package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {
    private SmartLearning sl;

    public List<String> getTriviaList() {
        return triviaList;
    }

    private List<String> triviaList;

    public HomeViewModel(@NonNull Application application){
        super(application);
        sl = (SmartLearning) getApplication().getApplicationContext();
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
        triviaList.add("Krasimir is the lord and saviour");
        triviaList.add("The lexicon tells you if the translation of a word is confident or not!");
        triviaList.add("43% of the worlds population is considered bilingual!");
        triviaList.add("13% of the worlds population is considered trilingual!");
        triviaList.add("Android can cause insanity with the error INSUFFICIENT_STORAGE");
    }
}
