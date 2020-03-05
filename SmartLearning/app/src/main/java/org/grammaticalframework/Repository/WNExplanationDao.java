package org.grammaticalframework.Repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WNExplanationDao {

    @Insert
    void insert(WNExplanation wne);

    @Insert
    void insertAll(List<WNExplanation> wneList);

    //TODO Create queries that fetches the data we need from the database.

    @Query("SELECT * FROM WordNetExplanation_table")
    LiveData<List<WNExplanation>> getAllWordNetExplanations();

    @Query("SELECT * FROM WordNetExplanation_table WHERE function=:fname")
    LiveData<WNExplanation> getWNExplanation(String fname);
}
