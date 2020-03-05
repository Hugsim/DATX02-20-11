package org.grammaticalframework.Repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WNExplanationRepository {
    private WNExplanationDao WNExplanationDao;
    private LiveData<List<WNExplanation>> allWordNetExplanations;

    public WNExplanationRepository(Application application){
        WNExplanationDatabase database = WNExplanationDatabase.getInstance(application);
        WNExplanationDao = database.wordNetExplanationDao();
        allWordNetExplanations = WNExplanationDao.getAllWordNetExplanations();
    }

    public void insert (WNExplanation wne){
        new InsertWordNetExplanationAsyncTask(WNExplanationDao).execute(wne);

    }

    public LiveData<List<WNExplanation>> getAllWordNetExplanations(){
        return allWordNetExplanations;
    }

    private static class InsertWordNetExplanationAsyncTask extends AsyncTask<WNExplanation, Void, Void>{
        private WNExplanationDao WNExplanationDao;

        private InsertWordNetExplanationAsyncTask (WNExplanationDao WNExplanationDao){
            this.WNExplanationDao = WNExplanationDao;
        }
        @Override
        protected Void doInBackground(WNExplanation... WNExplanations) {
            WNExplanationDao.insert(WNExplanations[0]);
            return null;
        }
    }

}
