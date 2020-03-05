package org.grammaticalframework.Repository;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WordNetExplanation_table")
public class WNExplanation {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String function;
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
