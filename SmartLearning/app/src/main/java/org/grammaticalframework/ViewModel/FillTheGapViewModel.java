package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import org.grammaticalframework.FillTheGapClass;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Concr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FillTheGapViewModel extends AndroidViewModel {


    private static final String TAG = FillTheGapViewModel.class.getSimpleName();
    Concr eng;
    Concr swe;

    private List<String> sentence = new ArrayList<>();
    private List<String> inflections = new ArrayList<>();
    private String redactedWord;
    private FillTheGapClass fillthegap;

    private GF gf;

    public FillTheGapViewModel(Application application){
        super(application);

        Log.d(TAG, "INSIDE OF FILLTHEGAPVIEWMODEL");

        SmartLearning mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        gf = new GF(mSmartLearning);
        eng = mSmartLearning.getSourceConcr();
        swe = mSmartLearning.getTargetConcr();

        //Expr e = Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (UseV eat_2_V) a_la_carte_Adv)))) NoVoc");

        fillthegap = new FillTheGapClass("PhrUtt NoPConj (UttS (AdvS (SubjS when_Subj (UseCl (TTAnt TPast ASimul) PPos (PredVP (DetCN (DetQuant IndefArt NumSg) (UseN studentMasc_1_N)) (ComplSlash (SlashV2a make_1_V2) (DetCN (DetQuant IndefArt NumSg) (AdjCN (PositA stupid_1_A) (UseN mistake_1_N))))))) (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron he_Pron) (ComplSlash (Slash2V3 spare_V3 (UsePron they_Pron)) (DetCN (DetQuant no_Quant NumSg) (UseN abuse_2_N))))))) NoVoc",
                eng,swe);

    }


    public String getSentence() {
        StringBuilder sb = new StringBuilder();
        for(String word : fillthegap.getTargetSentence()) {
            sb.append(word);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();

        //return gf.translateWord("apple");
    }

    public List<String> getInflections() {
        List<String> notAllInflections = new ArrayList<>();
        for(int i = 0; i < 5 && i < fillthegap.getInflections().size(); i++){
            notAllInflections.add(fillthegap.getInflections().get(i));
        }
        Collections.shuffle(notAllInflections);
        return notAllInflections;
    }

    public boolean checkCorrectAnswer(String answer){
        return fillthegap.checkCorrectAnswer(answer);
    }


    public void getNewSentence(){
        fillthegap = new FillTheGapClass("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (DetCN (DetQuant IndefArt NumPl) (UseN finger_1_N)) (AdvVP (UseV close_2_V) (PrepNP in_1_Prep (DetCN (DetQuant IndefArt NumSg) (AdjCN (PositA tight_1_A) (UseN fist_N)))))))) NoVoc",
                eng,swe);
    }

}
