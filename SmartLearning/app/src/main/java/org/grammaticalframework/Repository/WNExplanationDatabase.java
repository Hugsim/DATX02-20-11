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

@Database(entities = {WNExplanation.class}, version = 1, exportSchema = true)
public abstract class WNExplanationDatabase extends RoomDatabase {

    private static final String TAG = WNExplanationDatabase.class.getSimpleName();
    private  static WNExplanationDatabase INSTANCE;
    public abstract WNExplanationDao wordNetExplanationDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized WNExplanationDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (WNExplanationDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                                    WNExplanationDatabase.class, "wordnet.db")
                            .build();
                    //Populate db with the explanations
                    databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().insertAll(parseCsv(context)));
                }
            }
        }
        return INSTANCE;
    }

    public static List<WNExplanation> parseCsv(Context context) {
        LinkedList<WNExplanation> wneList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open("WordNet.csv")),';', ' ', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 2)
                    continue;
                wneList.add(new WNExplanation(nextLine[0], nextLine[1]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return wneList;
    }

}
