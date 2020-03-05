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

    //TODO Create queries that fetches the data we need from the database.

    @Query("SELECT * FROM WNExplanation ORDER BY priority DESC")
    LiveData<List<WNExplanation>> getAllWordNetExplanations();
}
