package org.grammaticalframework.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FillTheGapExercise_table")
public class FillTheGapExercise {

    @PrimaryKey
    @ColumnInfo(name = "abstractSyntaxTree")
    @NonNull
    private String abstractSyntaxTree;

    @ColumnInfo(name = "functionToReplace")
    private String functionToReplace;

    @NonNull
    @ColumnInfo(name = "tenseFunction")
    private String tenseFunction;

    public FillTheGapExercise(String abstractSyntaxTree, String functionToReplace, String tenseFunction){
        this.abstractSyntaxTree = abstractSyntaxTree;
        this.functionToReplace = functionToReplace;
        this.tenseFunction = tenseFunction;
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

    public String getTenseFunction(){return tenseFunction;}

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof FillTheGapExercise){
            FillTheGapExercise toCompare = (FillTheGapExercise) obj;
            return (this.abstractSyntaxTree.equals(toCompare.abstractSyntaxTree)
                    && this.functionToReplace.equals(toCompare.functionToReplace));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.functionToReplace.hashCode() + this.abstractSyntaxTree.hashCode();
    }

}
