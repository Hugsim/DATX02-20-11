package org.grammaticalframework.ViewModel;

import android.app.Application;

import org.grammaticalframework.Language;
import org.grammaticalframework.SmartLearning;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {
    private SmartLearning sl;

    public HomeViewModel(@NonNull Application application){
        super(application);
        sl = (SmartLearning) getApplication().getApplicationContext();
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
}
