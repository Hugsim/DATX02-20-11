package org.grammaticalframework.Repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WNExplanationRepository {
    
    private WNExplanationDao mWNExplanationDao;
    private LiveData<List<WNExplanation>> allWordNetExplanations;

    public WNExplanationRepository(Application application){
        GrammarlexDatabase database = GrammarlexDatabase.getInstance(application);
        mWNExplanationDao = database.wordNetExplanationDao();
        allWordNetExplanations = mWNExplanationDao.getAllWordNetExplanations();
    }

    public void insert (WNExplanation wne){
        GrammarlexDatabase.databaseWriteExecutor.execute(()-> {
            mWNExplanationDao.insert(wne);
        });
    }

    public void insertAll (List<WNExplanation> wneList){
        GrammarlexDatabase.databaseWriteExecutor.execute(()->{
            mWNExplanationDao.insertAll(wneList);
        });
    }

    public LiveData<List<WNExplanation>> getAllWordNetExplanations(){
        return allWordNetExplanations;
    }

    public LiveData<List<WNExplanation>> getWNExplanations(List<String> functions){
        return mWNExplanationDao.getAllWordNetExplanations(functions);
    }

    public LiveData<List<WNExplanationWithCheck>> getWNExplanationsWithCheck(List<String> functions, String langcode) {
        return mWNExplanationDao.getAllWordNetExplanationsWithCheck(functions, langcode);
    }

    public WNExplanation getWNExplanationSync(String function){
        return mWNExplanationDao.getWNExplanation(function);
    }

    public LiveData<List<WNExplanation>> getSynonyms (List<String> functions){
        return mWNExplanationDao.getWNSynonyms(functions);
    }

}
