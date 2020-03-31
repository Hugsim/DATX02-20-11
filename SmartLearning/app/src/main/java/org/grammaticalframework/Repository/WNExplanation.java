package org.grammaticalframework.Repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WordNetExplanation_table")
public class WNExplanation {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "function")
    @NonNull
    private String function;
    @ColumnInfo(name = "synonym")
    @NonNull
    private String synonym;
    @ColumnInfo(name = "explanation")
    @NonNull
    private String explanation;

    public WNExplanation(String function, String synonym, String explanation) {
        this.function = function;
        this.synonym = synonym;
        this.explanation = explanation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFunction() {
        return function;
    }

    public String getExplanation() {
        return explanation;
    }

    @NonNull
    public String getSynonym() {
        return synonym;
    }

}
