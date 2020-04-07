package org.grammaticalframework.Repository;

import android.app.Application;

import java.util.List;
import java.util.Set;

import androidx.collection.ArraySet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class FillTheGapExerciseRepository {

    private FillTheGapExerciseDao fillTheGapExerciseDao;
    private LiveData<List<FillTheGapExercise>> allFillTheGapExercises;
    private LiveData<FillTheGapExercise> unsolvedExercise;
    private MutableLiveData<Set<FillTheGapExercise>> solvedExercises = new MutableLiveData<>(new ArraySet<>());

    public FillTheGapExerciseRepository(Application application) {
        SmartLearningDatabase database = SmartLearningDatabase.getInstance(application);
        fillTheGapExerciseDao = database.fillTheGapExerciseDao();
        allFillTheGapExercises = fillTheGapExerciseDao.getAllFillTheGapExercises();
        unsolvedExercise = Transformations.switchMap(solvedExercises, solvedExercises -> {
            return Transformations.map(allFillTheGapExercises, exercises -> {
                for(FillTheGapExercise exercise : exercises){
                    if(!solvedExercises.contains(exercise)){
                        return exercise;
                    }
                }
                //no exercise was found
                return null;
            });
        });
    }

    public LiveData<FillTheGapExercise> getUnsolvedExercise() {
        return unsolvedExercise;
    }

    public void addSolvedExercise(FillTheGapExercise exercise){
        Set<FillTheGapExercise> temp = solvedExercises.getValue();
        if(temp == null)
            return;
        temp.add(exercise);
        solvedExercises.setValue(temp);
    }

    public void insert(FillTheGapExercise fillTheGapExercise) {
        SmartLearningDatabase.databaseWriteExecutor.execute(() -> {
            fillTheGapExerciseDao.insert(fillTheGapExercise);
        });
    }

    public void insertAll(List<FillTheGapExercise> fillTheGapExerciseList) {
        SmartLearningDatabase.databaseWriteExecutor.execute(() -> {
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
