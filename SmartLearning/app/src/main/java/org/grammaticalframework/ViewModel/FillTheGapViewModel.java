package org.grammaticalframework.ViewModel;

import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;

public class FillTheGapViewModel extends BaseViewModel {

    private SmartLearning sl;

    public FillTheGapViewModel(SmartLearning smartLearning){

        sl = smartLearning;

        //Hämta GF-parser-tjofräsen
        //Ladda in GF-parser-tjofräsen
        //Välj en övning
        //Ladda in övningen som ett eget objekt?
        generateExercise();

    }

    public String[] generateExercise() {
        Expr e = Expr.readExpr("abs: PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (ComplSlash (SlashV2a abandon_1_V2) (DetCN (DetQuant DefArt NumSg) (AdjCN (PositA old_1_A) (UseN car_1_N)))) (PrepNP in_1_Prep (DetCN (DetQuant DefArt NumSg) (AdjCN (PositA empty_1_A) (UseN (CompoundN parking_1_N lot_2_N))))))))) NoVoc");

        Concr eng = sl.getSoruceConcr();
        Concr swe = sl.getTargetConcr();



        String[] exercise = new String[1];
        exercise[0] = "hej";
        return exercise;

    }


}
