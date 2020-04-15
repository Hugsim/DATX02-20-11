package org.grammaticalframework.Repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "TranslateExercises")
public class TranslateExercise {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "translateFunction")
    private String translateFunction; //The translateWord that is displayed in the question

    @NonNull
    @ColumnInfo(name = "alternativeFunctions")
    @TypeConverters(Converter.class)
    private List<String> alternativeFunctions; //The alternative answers

    public TranslateExercise(String translateFunction, List<String> alternativeFunctions) {
        this.translateFunction = translateFunction;
        this.alternativeFunctions = alternativeFunctions;
    }

    public String getTranslateFunction() {
        return translateFunction;
    }

    public List<String> getAlternativeFunctions() {
        return alternativeFunctions;
    }

}
