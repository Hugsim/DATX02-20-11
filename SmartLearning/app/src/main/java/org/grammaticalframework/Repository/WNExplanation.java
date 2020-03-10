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
    @ColumnInfo(name = "explanation")
    @NonNull
    private String explanation;

    public WNExplanation(String function, String explanation) {
        this.function = function;
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
}
