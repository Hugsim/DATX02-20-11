package org.grammaticalframework.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CheckedFunctionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CheckedFunction checkedFunction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CheckedFunction> checkedFunctionList);

    @Query("SELECT * FROM CheckedFunction_table")
    LiveData<List<CheckedFunction>> getAllCheckedFunctions();

    /*
    Not needed as we will only be inserting checked functions
    @Query("SELECT * FROM CheckedFunction_table WHERE function = :function AND langcode = :langcode")
    LiveData<CheckedFunction> getCheckedFunction(String function, String langcode);

    @Query("SELECT * FROM CheckedFunction_table WHERE function IN (:functions) AND langcode = :langcode")
    LiveData<List<CheckedFunction>> getCheckedFunctions(List<String> functions, String langcode);
     */
}
