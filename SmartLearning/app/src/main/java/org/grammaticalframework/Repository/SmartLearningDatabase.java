package org.grammaticalframework.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WNExplanation.class, FillTheGapExercise.class, CheckedFunction.class, SynonymExercise.class}, version = 1, exportSchema = false)
@TypeConverters(SynonymExercise.Converter.class)
public abstract class SmartLearningDatabase extends RoomDatabase {

    private static final String TAG = SmartLearningDatabase.class.getSimpleName();
    private  static SmartLearningDatabase INSTANCE;

    public abstract WNExplanationDao wordNetExplanationDao();
    public abstract FillTheGapExerciseDao fillTheGapExerciseDao();
    public abstract CheckedFunctionDao checkedFunctionDao();
    public abstract SynonymExerciseDao synonymExerciseDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized SmartLearningDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (SmartLearningDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SmartLearningDatabase.class, "smartlearning.db")
                            //.createFromAsset("databases/smartlearning.db")
                            //TODO: create new db file with new schema and some synonymExercises
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    //Code for writing from the csv-files to the database.
                                    //Only needed when creating a new database, it should be quicker to load in the db that is already created.

                                    /*
                                    databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().insertAll(ParseUtils.parseExplanationCSV(context, "parsing/WordNet.csv")));
                                    databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(ParseUtils.parseFillTheGapExerciseCSV(context, "parsing/Exercises.csv")));
                                    databaseWriteExecutor.execute(() -> INSTANCE.checkedFunctionDao().insertAll(ParseUtils.parseWordNetChecks(context, "parsing/WordNetChecked.csv")));*/
                                    databaseWriteExecutor.execute(()-> INSTANCE.synonymExerciseDao().insertAll(ParseUtils.parseSynonymExerciseCSV(context, "parsing/SynonymExercises.csv")));
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
