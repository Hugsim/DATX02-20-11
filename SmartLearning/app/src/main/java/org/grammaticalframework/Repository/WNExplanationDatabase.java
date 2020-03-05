package org.grammaticalframework.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = WNExplanation.class, version = 1)
public abstract class WNExplanationDatabase extends RoomDatabase {

    private static final String TAG = WNExplanationDatabase.class.getSimpleName();
    private  static WNExplanationDatabase instance;

    public abstract WNExplanationDao wordNetExplanationDao();

    public static synchronized WNExplanationDatabase getInstance(Context context){
        //TODO: something is not working, not sure what.
        if (instance == null){
            Log.d("Henrik", "kommer till createDB");
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WNExplanationDatabase.class, "wordNetExplanation_database.db")
                    .addCallback(new RoomDatabase.Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d("Henrik", "kommer till onCreate");

                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    // insert data using DAO
                                    getInstance(context).wordNetExplanationDao().insertAll(getInstance(context).parseCsv(context));
                                }
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    public static List<WNExplanation> parseCsv(Context context) {
        Log.d("Henrik", "kommer till parseCSV");

        LinkedList<WNExplanation> wneList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open("WordNet.csv")));
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                Log.d(TAG, nextLine.toString());
                wneList.add(new WNExplanation(nextLine[0], nextLine[1]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        Log.d("Henrik", "kommer till skicka tillbaka listan, listans size: " + wneList.size());
        return wneList;
    }

}
