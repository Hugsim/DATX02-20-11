package org.grammaticalframework.Repository;

import android.app.Application;

import java.util.List;
import java.util.Set;

import androidx.collection.ArraySet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ExerciseRepository {

    private FillTheGapExerciseDao fillTheGapExerciseDao;
    private LiveData<List<FillTheGapExercise>> allFillTheGapExercises;
    private LiveData<FillTheGapExercise> unsolvedFillTheGapExercise;
    private MutableLiveData<Set<FillTheGapExercise>> solvedFillTheGapExercises = new MutableLiveData<>(new ArraySet<>());

    private SynonymExerciseDao synonymExerciseDao;
    private LiveData<List<SynonymExercise>> allSynonymExercises;
    private LiveData<SynonymExercise> unsolvedSynonymExercise;
    private MutableLiveData<Set<SynonymExercise>> solvedSynonymExercises = new MutableLiveData<>(new ArraySet<>());

    private TranslateExerciseDao translateExerciseDao;
    private LiveData<List<TranslateExercise>> allTranslateExercises;
    private LiveData<TranslateExercise> unsolvedTranslateExercises;
    private MutableLiveData<Set<TranslateExercise>> solvedTranslateExercises = new MutableLiveData<>(new ArraySet<>());

    public ExerciseRepository(Application application) {
        GrammarlexDatabase database = GrammarlexDatabase.getInstance(application);
        fillTheGapExerciseDao = database.fillTheGapExerciseDao();
        allFillTheGapExercises = fillTheGapExerciseDao.getAllFillTheGapExercises();
        unsolvedFillTheGapExercise = Transformations.switchMap(solvedFillTheGapExercises, solvedExercises -> {
            return Transformations.map(allFillTheGapExercises, exercises -> {
                for(FillTheGapExercise exercise : exercises){
                    if(!solvedExercises.contains(exercise)){
                        if(allFillTheGapExercises.getValue().size() - 1 == solvedExercises.size()){
                            solvedExercises.clear();
                        }
                        return exercise;
                    }
                }
                //no exercise was found
                return null;
            });
        });

        synonymExerciseDao = database.synonymExerciseDao();
        allSynonymExercises = synonymExerciseDao.getAllSynonymExercises();
        unsolvedSynonymExercise = Transformations.switchMap(solvedSynonymExercises, solvedExercises -> {
            return Transformations.map(allSynonymExercises, exercises -> {
                for(SynonymExercise exercise : exercises){
                    if(!solvedExercises.contains(exercise)){
                        if(allSynonymExercises.getValue().size() - 1 == solvedExercises.size()){
                            solvedExercises.clear();
                        }
                        return exercise;
                    }
                }
                //no exercise was found
                return null;
            });
        });

        translateExerciseDao = database.translateExerciseDao();
        allTranslateExercises = translateExerciseDao.getAllTranslateExercises();
        unsolvedTranslateExercises = Transformations.switchMap(solvedTranslateExercises, solvedExercises -> {
            return Transformations.map(allTranslateExercises, exercises -> {
                for (TranslateExercise exercise : exercises){
                    if(!solvedExercises.contains(exercise)){
                        if (allTranslateExercises.getValue().size() - 1 == solvedExercises.size()){
                            solvedExercises.clear();
                        }
                        return exercise;
                    }
                }
                return null;
            });
        });
    }

    public LiveData<FillTheGapExercise> getUnsolvedFillTheGapExercise() {
        return unsolvedFillTheGapExercise;
    }

    public void addSolvedFillTheGapExercise(FillTheGapExercise exercise){
        Set<FillTheGapExercise> temp = solvedFillTheGapExercises.getValue();
        if(temp == null)
            return;
        temp.add(exercise);
        solvedFillTheGapExercises.setValue(temp);
    }

    public void insert(FillTheGapExercise fillTheGapExercise) {
        GrammarlexDatabase.databaseWriteExecutor.execute(() -> {
            fillTheGapExerciseDao.insert(fillTheGapExercise);
        });
    }

    public void insertAll(List<FillTheGapExercise> fillTheGapExerciseList) {
        GrammarlexDatabase.databaseWriteExecutor.execute(() -> {
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

    //Synonym Exercises

    public LiveData<SynonymExercise> getunsolvedSynonymExercise() {
        return unsolvedSynonymExercise;
    }

    public void addSolvedSynonymExercise(SynonymExercise exercise){
        Set<SynonymExercise> temp = solvedSynonymExercises.getValue();
        if(temp == null)
            return;
        temp.add(exercise);
        solvedSynonymExercises.setValue(temp);
    }

    //Translate Exercises
    public LiveData<TranslateExercise> getunsolvedTranslateExercise() {
        return unsolvedTranslateExercises;
    }

    public void addSolvedTranslateExercise(TranslateExercise exercise){
        Set<TranslateExercise> temp = solvedTranslateExercises.getValue();
        if(temp == null)
            return;
        temp.add(exercise);
        solvedTranslateExercises.setValue(temp);
    }
}
