package org.grammaticalframework.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = WNExplanation.class, version = 1)
public abstract class WNExplanationDatabase extends RoomDatabase {

    private  static WNExplanationDatabase instance;

    public abstract WNExplanationDao wordNetExplanationDao();


    public static synchronized WNExplanationDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WNExplanationDatabase.class, "wordNetExplanation_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private WNExplanationDao WNExplanationDao;

        private PopulateDbAsyncTask(WNExplanationDatabase db){
            WNExplanationDao = db.wordNetExplanationDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //TODO POPULATE DB WITH CVS INFORMATION
            //WNExplanationDao.insert();
            return null;
        }
    }






}
