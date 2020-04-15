package org.grammaticalframework.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WNExplanation.class, FillTheGapExercise.class, CheckedFunction.class, SynonymExercise.class, TranslateExercise.class}, version = 1, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class GrammarlexDatabase extends RoomDatabase {

    private static final String TAG = GrammarlexDatabase.class.getSimpleName();
    private  static GrammarlexDatabase INSTANCE;

    public abstract WNExplanationDao wordNetExplanationDao();
    public abstract FillTheGapExerciseDao fillTheGapExerciseDao();
    public abstract CheckedFunctionDao checkedFunctionDao();
    public abstract SynonymExerciseDao synonymExerciseDao();
    public abstract TranslateExerciseDao translateExerciseDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized GrammarlexDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (GrammarlexDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GrammarlexDatabase.class, "grammarlex.db")
                            .createFromAsset("databases/grammarlex.db")
                            //TODO: create new db file with new schema and some synonymExercises
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    //Code for writing from the csv-files to the database.
                                    //Only needed when creating a new database, it should be quicker to load in the db that is already created.
                                    //databaseWriteExecutor.execute(() -> INSTANCE.wordNetExplanationDao().insertAll(ParseUtils.parseExplanationCSV(context, "parsing/WordNet.csv")));
                                    //databaseWriteExecutor.execute(() -> INSTANCE.fillTheGapExerciseDao().insertAll(ParseUtils.parseFillTheGapExerciseCSV(context, "parsing/Exercises.csv")));
                                    //databaseWriteExecutor.execute(() -> INSTANCE.checkedFunctionDao().insertAll(ParseUtils.parseWordNetChecks(context, "parsing/WordNetChecked.csv")));
                                    //databaseWriteExecutor.execute(()-> INSTANCE.synonymExerciseDao().insertAll(ParseUtils.parseSynonymExerciseCSV(context, "parsing/SynonymExercises.csv")));
                                    //databaseWriteExecutor.execute(()-> INSTANCE.translateExerciseDao().insertAll(ParseUtils.parseTranslateExerciseCSV(context, "parsing/TranslateExercises.csv")));
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
