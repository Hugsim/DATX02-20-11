package org.grammaticalframework.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TranslateExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TranslateExercise se);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TranslateExercise> seList);

    @Query("SELECT * FROM TranslateExercises")
    LiveData<List<TranslateExercise>> getAllTranslateExercises();
}
