package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import org.grammaticalframework.FillTheGapClass;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FillTheGapViewModel extends AndroidViewModel {


    private static final String TAG = FillTheGapViewModel.class.getSimpleName();
    Concr source;
    Concr target;
    private FillTheGapClass fillthegap;
    private GF gf;

    public FillTheGapViewModel(Application application){
        super(application);

        Log.d(TAG, "INSIDE OF FILLTHEGAPVIEWMODEL");

        SmartLearning mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        gf = new GF(mSmartLearning);
        source = mSmartLearning.getSourceConcr();
        target = mSmartLearning.getTargetConcr();
        fillthegap = new FillTheGapClass("PhrUtt NoPConj (UttS (AdvS (SubjS when_Subj (UseCl (TTAnt TPast ASimul) PPos (PredVP (DetCN (DetQuant IndefArt NumSg) (UseN studentMasc_1_N)) (ComplSlash (SlashV2a make_1_V2) (DetCN (DetQuant IndefArt NumSg) (AdjCN (PositA stupid_1_A) (UseN mistake_1_N))))))) (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron he_Pron) (ComplSlash (Slash2V3 spare_V3 (UsePron they_Pron)) (DetCN (DetQuant no_Quant NumSg) (UseN abuse_2_N))))))) NoVoc"
);
        sentenceToExpr();
        bracketLinearize();
        setRedactedWord();
    }
    private void sentenceToExpr(){
        fillthegap.setExpr(Expr.readExpr(fillthegap.getSentence()));
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
        if(answer.equals(fillthegap.getRedactedWord())){
            return true;
        } else{
            return false;
        }
    }


    public void getNewSentence(){
        fillthegap = new FillTheGapClass("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (DetCN (DetQuant IndefArt NumPl) (UseN finger_1_N)) (AdvVP (UseV close_2_V) (PrepNP in_1_Prep (DetCN (DetQuant IndefArt NumSg) (AdjCN (PositA tight_1_A) (UseN fist_N)))))))) NoVoc");
    }

    private void bracketLinearize(){
        fillthegap.setBs(target.bracketedLinearize(fillthegap.getExpr()));
        scanBracket(fillthegap.getBs()[0]);
    }

    private void scanBracket(Object bs) {
        Log.d(TAG, "Bracketedscanned");
        if(bs instanceof Bracket){
            Log.d(TAG, ":DDDD");
            if(((Bracket) bs).cat.equals("V")|| ((Bracket) bs).cat.equals("V2")|| ((Bracket) bs).cat.equals("V3")){
                Log.d(TAG, "___________________________________________");
                String verb = ((Bracket) bs).fun;
                inflect(verb);
                return; //After the first verb has been found, stop
            }
            if (((Bracket) bs).children != null){
                for(Object child : ((Bracket) bs).children){
                    scanBracket(child);
                }
            }
        }else if(bs instanceof String){
        }
    }

    private void inflect(String verb) {
        Log.d(TAG, verb);
        Expr e = Expr.readExpr(verb);
        for(Map.Entry<String,String> entry  : target.tabularLinearize(e).entrySet()) {
            if(entry.getKey().contains("Act")){
                fillthegap.getInflections().add(entry.getValue()); //Add inflections
            }
        }

    }

    private void setRedactedWord(){
        String targetLinearization = target.linearize(fillthegap.getExpr());
        for(String word : targetLinearization.split(" ")){
            fillthegap.getTargetSentence().add(word);
        }
        //Prepend the correct inflection to inflections
        for(String word : fillthegap.getInflections()) {
            if(targetLinearization.contains(word)){
                String temp = fillthegap.getInflections().get(0);
                int indexInflection = fillthegap.getInflections().indexOf(word);
                fillthegap.getInflections().set(0, word);
                fillthegap.getInflections().set(indexInflection, temp);
                fillthegap.setRedactedWord(word);
            }
        }
        if(fillthegap.getTargetSentence().contains(fillthegap.getRedactedWord())) {
            Log.d(TAG, "ooga booga");
            int toRemove = fillthegap.getTargetSentence().indexOf(fillthegap.getRedactedWord());
            String redacted = "";
            for (char c : fillthegap.getRedactedWord().toCharArray()) {
                redacted += "_";
                fillthegap.getTargetSentence().set(toRemove, redacted);
            }
        }
    }

}
