package org.grammaticalframework.Repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WNExplanation.class, FillTheGapExercise.class}, version = 2, exportSchema = false)
public abstract class WNExplanationDatabase extends RoomDatabase {

    private static final String TAG = WNExplanationDatabase.class.getSimpleName();
    private  static WNExplanationDatabase INSTANCE;
    public abstract WNExplanationDao wordNetExplanationDao();

    public abstract FillTheGapExerciseDao fillTheGapExerciseDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized WNExplanationDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (WNExplanationDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WNExplanationDatabase.class, "smartlearning.db")
                            .createFromAsset("databases/smartlearning.db")
                            .build();

                    //empty the database
                    //databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().deleteAll());
                    //databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().deleteAll());

                    //Populate db with the explanations & exercises
                    //databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().insertAll(parseExplanationCSV(context, "WordNet.csv")));
                    //databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(parseFillTheGapExerciseCSV(context, "Exercises.csv")));
                }
            }
        }
        return INSTANCE;
    }

    public static List<WNExplanation> parseExplanationCSV(Context context, String fileName) {
        LinkedList<WNExplanation> wneList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 3)
                    continue;
                wneList.add(new WNExplanation(nextLine[0], nextLine[1], nextLine[2]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return wneList;
    }

    public static List<FillTheGapExercise> parseFillTheGapExerciseCSV(Context context, String fileName) {
        LinkedList<FillTheGapExercise> fillTheGapExerciseList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 2)
                    continue;
                fillTheGapExerciseList.add(new FillTheGapExercise(nextLine[0], nextLine[1]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return fillTheGapExerciseList;
    }

}
