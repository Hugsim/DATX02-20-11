package org.grammaticalframework.Repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WNExplanationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WNExplanation wne);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WNExplanation> wneList);

    @Query("DELETE FROM WordNetExplanation_table")
    void deleteAll();

    //TODO Create queries that fetches the data we need from the database.

    @Query("SELECT * FROM WordNetExplanation_table")
    LiveData<List<WNExplanation>> getAllWordNetExplanations();

    @Query("SELECT * FROM WordNetExplanation_table WHERE function = :fname")
    WNExplanation getWNExplanation(String fname);

    @Query("SELECT * FROM WordNetExplanation_table WHERE function IN (:functions)")
    LiveData<List<WNExplanation>> getAllWordNetExplanations(List<String> functions);

    @Query("SELECT * FROM WordNetExplanation_table WHERE synonym IN (:synonyms) AND explanation != 'There is no explanation available for this word'")
    LiveData<List<WNExplanation>> getWNSynonyms (List<String> synonyms);

}
