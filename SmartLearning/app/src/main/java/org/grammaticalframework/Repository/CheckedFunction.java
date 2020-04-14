package org.grammaticalframework.Repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A class which contains a function and whether or not it is checked in the WordNet port
 */

@Entity(tableName = "CheckedFunction_table", primaryKeys = {"checked_function", "langcode"})
public class CheckedFunction {

    @ColumnInfo(name = "checked_function")
    @NonNull
    private String function;

    @ColumnInfo(name = "status")
    @NonNull
    private String status;

    @ColumnInfo(name = "langcode")
    @NonNull
    private String langcode;

    public CheckedFunction(String function, String status, String langcode) {
        this.function = function;
        this.status = status;
        this.langcode = langcode;
    }

    @NonNull
    public String getFunction() {
        return function;
    }

    public void setFunction(@NonNull String function) {
        this.function = function;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(@NonNull String langcode) {
        this.langcode = langcode;
    }
}
