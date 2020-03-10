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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WNExplanation.class}, version = 1, exportSchema = false)
public abstract class WNExplanationDatabase extends RoomDatabase {

    private static final String TAG = WNExplanationDatabase.class.getSimpleName();
    private  static WNExplanationDatabase INSTANCE;
    public abstract WNExplanationDao wordNetExplanationDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized WNExplanationDatabase getInstance(final Context context){
        //TODO: something is not working, not sure what.
        if(INSTANCE == null){
            synchronized (WNExplanationDatabase.class){
                if (INSTANCE == null){
                    System.out.println("Hej");
                    Log.d("Henrik", "kommer till createDB1");
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                                    WNExplanationDatabase.class, "WordNet.db")
                            .createFromAsset("WordNet.db")
                            //.addCallback(callback)
                            .build();
                }
            }
        }
        Log.d("Henrik", "kommer till createDB4");
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // insert data using DAO
            Log.d("Henrik", "kommer till createDB3");

            databaseWriteExecutor.execute(()->{
                WNExplanationDao dao = INSTANCE.wordNetExplanationDao();
                dao.deleteAll();

                WNExplanation wne = new WNExplanation("AFK", "Henrik is nice");
                dao.insert(wne);
            });

            //INSTANCE.wordNetExplanationDao().insertAll(INSTANCE.parseCsv());
        }
    };

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
