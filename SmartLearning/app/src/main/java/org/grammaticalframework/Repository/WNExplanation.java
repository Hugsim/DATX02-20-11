package org.grammaticalframework.Repository;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WordNetExplanation_table")
public class WNExplanation {

    @PrimaryKey(autoGenerate = true)

    private int id;

    private int word;

    private String explanation;

    private int priority;

    public WNExplanation(int word, String explanation, int priority) {
        this.word = word;
        this.explanation = explanation;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPriority(){
        return priority;
    }

    public int getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }
}
