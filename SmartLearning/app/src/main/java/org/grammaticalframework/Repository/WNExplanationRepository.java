package org.grammaticalframework.Repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WNExplanationRepository {
    private WNExplanationDao mWNExplanationDao;
    private LiveData<List<WNExplanation>> allWordNetExplanations;

    public WNExplanationRepository(Application application){
        WNExplanationDatabase database = WNExplanationDatabase.getInstance(application);
        mWNExplanationDao = database.wordNetExplanationDao();
        allWordNetExplanations = mWNExplanationDao.getAllWordNetExplanations();
    }

    public void insert (WNExplanation wne){
        WNExplanationDatabase.databaseWriteExecutor.execute(()-> {
            mWNExplanationDao.insert(wne);
        });
    }

    public void insertAll (List<WNExplanation> wneList){
        WNExplanationDatabase.databaseWriteExecutor.execute(()->{
            mWNExplanationDao.insertAll(wneList);
        });
    }

    public LiveData<List<WNExplanation>> getAllWordNetExplanations(){
        return allWordNetExplanations;
    }
}
