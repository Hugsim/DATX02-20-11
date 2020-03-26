package org.grammaticalframework.Repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FillTheGapExercise_table")
public class FillTheGapExercise {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "abstractSyntaxTree")
    @NonNull
    private String abstractSyntaxTree;

    @ColumnInfo(name = "functionToReplace")
    private String functionToReplace;

    public FillTheGapExercise(String abstractSyntaxTree, String functionToReplace){
        this.abstractSyntaxTree = abstractSyntaxTree;
        this.functionToReplace = functionToReplace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAbstractSyntaxTree() {
        return abstractSyntaxTree;
    }

    public void setAbstractSyntaxTree(@NonNull String abstractSyntaxTree) {
        this.abstractSyntaxTree = abstractSyntaxTree;
    }

    public String getFunctionToReplace() {
        return functionToReplace;
    }

    public void setFunctionToReplace(String functionToReplace) {
        this.functionToReplace = functionToReplace;
    }
}
