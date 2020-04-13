package org.grammaticalframework.Repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WNExplanationRepository {
    
    private WNExplanationDao mWNExplanationDao;

    public WNExplanationRepository(Application application){
        SmartLearningDatabase database = SmartLearningDatabase.getInstance(application);
        mWNExplanationDao = database.wordNetExplanationDao();
    }

    public void insert (WNExplanation wne){
        SmartLearningDatabase.databaseWriteExecutor.execute(()-> {
            mWNExplanationDao.insert(wne);
        });
    }

    public void insertAll (List<WNExplanation> wneList){
        SmartLearningDatabase.databaseWriteExecutor.execute(()->{
            mWNExplanationDao.insertAll(wneList);
        });
    }

    public LiveData<List<WNExplanation>> getWNExplanations(List<String> functions){
        return mWNExplanationDao.getAllWordNetExplanations(functions);
    }

    public LiveData<List<WNExplanationWithCheck>> getWNExplanationsWithCheck(List<String> functions, String langcode) {
        return mWNExplanationDao.getAllWordNetExplanationsWithCheck(functions, langcode);
    }

    public LiveData<List<WNExplanation>> getSynonyms (List<String> functions){
        return mWNExplanationDao.getWNSynonyms(functions);
    }

}
