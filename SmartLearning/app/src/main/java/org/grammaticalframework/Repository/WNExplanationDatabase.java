package org.grammaticalframework.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WNExplanation.class, FillTheGapExercise.class, CheckedFunction.class}, version = 3, exportSchema = false)
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
                            //.createFromAsset("databases/smartlearning.db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().insertAll(ParseUtils.parseExplanationCSV(context, "parsing/WordNet.csv")));
                                    databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(ParseUtils.parseFillTheGapExerciseCSV(context, "parsing/Exercises.csv")));
                                    databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(ParseUtils.parseFillTheGapExerciseCSV(context, "parsing/WordNetEngChecked.csv")));
                                    databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(ParseUtils.parseFillTheGapExerciseCSV(context, "parsing/wordNetSweChecked.csv")));
                                }
                            })
                            .build();

                    //empty the database
                    //databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().deleteAll());
                    //databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().deleteAll());

                    //Populate db with the explanations & exercises

                }
            }
        }
        return INSTANCE;
    }





}
