package org.grammaticalframework.Repository;

import android.content.Context;
import android.util.Log;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ParseUtils {
    final static private String TAG = ParseUtils.class.getSimpleName();

    public static List<WNExplanation> parseExplanationCSV(Context context, String fileName) {
        LinkedList<WNExplanation> wneList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 3)
                    continue;
                wneList.add(new WNExplanation(nextLine[0], nextLine[1], nextLine[2]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return wneList;
    }

    public static List<FillTheGapExercise> parseFillTheGapExerciseCSV(Context context, String fileName) {
        LinkedList<FillTheGapExercise> fillTheGapExerciseList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 3)
                    continue;
                fillTheGapExerciseList.add(new FillTheGapExercise(nextLine[0], nextLine[1], nextLine[2]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return fillTheGapExerciseList;
    }

    public static List<CheckedFunction> parseWordNetChecks(Context context, String fileName) {
        LinkedList<CheckedFunction> checkedFunctions = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 3)
                    continue;
                checkedFunctions.add(new CheckedFunction(nextLine[0], nextLine[1], nextLine[2]));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return checkedFunctions;
    }

    public static List<SynonymExercise> parseSynonymExerciseCSV(Context context, String fileName) {
        LinkedList<SynonymExercise> synonymExerciseList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 4)
                    continue;
                synonymExerciseList.add(new SynonymExercise(nextLine[0], nextLine[1], nextLine[2], Arrays.asList(nextLine[3].split(","))));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return synonymExerciseList;
    }

    public static List<TranslateExercise> parseTranslateExerciseCSV(Context context, String fileName) {
        LinkedList<TranslateExercise> translateExerciseList = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(context.getAssets().open(fileName)),';', '"', 1);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine.length < 2)
                    continue;
                translateExerciseList.add(new TranslateExercise(nextLine[0], Arrays.asList(nextLine[1].split(","))));
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return translateExerciseList;
    }
}
