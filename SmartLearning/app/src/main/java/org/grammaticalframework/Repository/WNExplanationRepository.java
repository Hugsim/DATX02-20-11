package org.grammaticalframework.Repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WNExplanationRepository {
    
    private WNExplanationDao mWNExplanationDao;
    private LiveData<List<WNExplanation>> allWordNetExplanations;

    public WNExplanationRepository(Application application){
        SmartLearningDatabase database = SmartLearningDatabase.getInstance(application);
        mWNExplanationDao = database.wordNetExplanationDao();
        allWordNetExplanations = mWNExplanationDao.getAllWordNetExplanations();
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

    public LiveData<List<WNExplanation>> getAllWordNetExplanations(){
        return allWordNetExplanations;
    }

    public LiveData<List<WNExplanation>> getWNExplanations(List<String> functions){
        return mWNExplanationDao.getAllWordNetExplanations(functions);
    }

    public WNExplanation getWNExplanationSync(String function){
        return mWNExplanationDao.getWNExplanation(function);
    }

    public LiveData<List<WNExplanation>> getSynonyms (List<String> functions){
        return mWNExplanationDao.getWNSynonyms(functions);
    }

}
