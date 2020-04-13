package org.grammaticalframework.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SynonymExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SynonymExercise se);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SynonymExercise> seList);

    @Query("SELECT * FROM SynonymExercises")
    LiveData<List<SynonymExercise>> getAllSynonymExercises();
}
