package org.grammaticalframework.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "SynonymExercises")
public class SynonymExercise {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "synonymFunction")
    private String synonymFunction; //The synonym that is displayed in the question

    @NonNull
    @ColumnInfo(name = "answerFunction")
    private String answerFunction; //The synonym which is an answer alternative

    @NonNull
    @ColumnInfo(name = "synonymCode")
    private String synonymCode; //The code for the synonyms

    @NonNull
    @ColumnInfo(name = "alternativeFunctions")
    @TypeConverters(Converter.class)
    private List<String> alternativeFunctions; //The alternative answers

    public SynonymExercise(String synonymFunction, String answerFunction, String synonymCode, List<String> alternativeFunctions) {
        this.synonymFunction = synonymFunction;
        this.answerFunction = answerFunction;
        this.synonymCode = synonymCode;
        this.alternativeFunctions = alternativeFunctions;
    }

    public String getSynonymFunction() {
        return synonymFunction;
    }

    public String getAnswerFunction() {
        return answerFunction;
    }

    public String getSynonymCode() {
        return synonymCode;
    }

    public List<String> getAlternativeFunctions() {
        return alternativeFunctions;
    }

}
