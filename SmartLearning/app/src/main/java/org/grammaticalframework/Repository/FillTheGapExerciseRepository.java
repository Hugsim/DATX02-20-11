package org.grammaticalframework.Repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FillTheGapExerciseRepository {

    private FillTheGapExerciseDao fillTheGapExerciseDao;
    private LiveData<List<FillTheGapExercise>> allFillTheGapExercises;

    public FillTheGapExerciseRepository(Application application) {
        WNExplanationDatabase database = WNExplanationDatabase.getInstance(application);
        fillTheGapExerciseDao = database.fillTheGapExerciseDao();
        allFillTheGapExercises = fillTheGapExerciseDao.getAllFillTheGapExercises();
    }

    public void insert(FillTheGapExercise fillTheGapExercise) {
        WNExplanationDatabase.databaseWriteExecutor.execute(() -> {
            fillTheGapExerciseDao.insert(fillTheGapExercise);
        });
    }

    public void insertAll(List<FillTheGapExercise> fillTheGapExerciseList) {
        WNExplanationDatabase.databaseWriteExecutor.execute(() -> {
            fillTheGapExerciseDao.insertAll(fillTheGapExerciseList);
        });
    }

    public LiveData<List<FillTheGapExercise>> getAllFillTheGapExercises() {
        return allFillTheGapExercises;
    }

    public LiveData<List<FillTheGapExercise>> getFillTheGapExercises(List<String> functionsToReplace) {
        return fillTheGapExerciseDao.getAllFillTheGapExercises(functionsToReplace);
    }

    public LiveData<FillTheGapExercise> getFillTheGapExercise(String functionToReplace) {
        return fillTheGapExerciseDao.getFillTheGapExercise(functionToReplace);
    }
}
