package org.grammaticalframework.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FillTheGapExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FillTheGapExercise fillTheGapExercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FillTheGapExercise> fillTheGapExerciseList);

    @Query("DELETE FROM FillTheGapExercise_table")
    void deleteAll();

    @Query("SELECT * FROM FillTheGapExercise_table")
    LiveData<List<FillTheGapExercise>> getAllFillTheGapExercises();

    @Query("SELECT * FROM FillTheGapExercise_table WHERE functionToReplace = :functionToReplace")
    LiveData<FillTheGapExercise> getFillTheGapExercise(String functionToReplace);

    @Query("SELECT * FROM FillTheGapExercise_table WHERE functionToReplace IN (:functionsToReplace)")
    LiveData<List<FillTheGapExercise>> getAllFillTheGapExercises(List<String> functionsToReplace);

}
