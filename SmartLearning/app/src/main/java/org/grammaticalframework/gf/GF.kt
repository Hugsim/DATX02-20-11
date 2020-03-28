package org.grammaticalframework.gf

import android.util.Log
import org.grammaticalframework.Language

import org.grammaticalframework.SmartLearning
import org.grammaticalframework.pgf.Concr
import org.grammaticalframework.pgf.Expr
import org.grammaticalframework.pgf.PGF

const val TAG = "Kotl/GF"

/*

Expr: A syntax tree
  Created by: Expr Expr.readExpr(String)
    where the String is a representation of a syntax tree

Concr: The grammar of a language
  Created by: Gotten from a PGF-file
    or, in our case, the SmartLearning object

PGF: A set of grammars
  Created by: PGF.readPGF(Filepath)
    or, in our case, the SmartLearning object

ExprProb: An expression with a probability of correct parsing
  Created via: [concr].parse([pgf].getStartCat(), [concr_string]) gives an Iterable, which via .iterator() gives an Iterator<ExprProb>
    Has methods:
      .getExpr()
      .getProb() where a smaller value means a greater probability

 */

class GF(var smartLearningInstance: SmartLearning) {

    val sourceConcr: Concr
        get() = smartLearningInstance.sourceConcr!!

    val targetConcr: Concr
        get() = smartLearningInstance.targetConcr!!

    val sourceLang: Language
        get() = smartLearningInstance.sourceLanguage!!

    val targetLang: Language
        get() = smartLearningInstance.targetLanguage!!

    val pgf: PGF
        get() = smartLearningInstance.pgf!!

    /* fun translateWord(str: String): String {
        val answer = linearize(parse(str, sourceConcr), targetConcr)
        Log.d(TAG, "Translated \"${str}\" into \"$answer\"")

        Log.d(TAG, partOfSpeech("play_1_V"))
        Log.d(TAG, generateInflectionTable("apple_1_N"))

        //recurse(Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (AdvVP (UseV eat_2_V) a_la_carte_Adv) today_2_Adv)))) NoVoc"))

        return answer
    } */

    fun recurse(ea: Expr) {
        ea.unApp() ?. let {
            Log.d("RECURSE", it.function + " - " + it.arguments.size + " - " +  linearize(Expr.readExpr(it.function), sourceConcr))

            for (e in it.arguments) {
                recurse(e)
            }
        } ?: return
    }

    fun functionTypeOf(function: String): String {
        return smartLearningInstance.grammar.getFunctionType(function).category
    }

    fun partOfSpeech(function: String): String {
        return linearize(Expr.readExpr("MkTag (Inflection${functionTypeOf(function)} ${function})"), targetConcr)
    }

    fun generateInflectionTable(function: String): String {
        return linearize(Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection${functionTypeOf(function)} ${function}) \"\""), targetConcr)
    }

    fun readExpr(expr: String): Expr {
        return Expr.readExpr(expr)
    }

    fun translate(sentence: String): String {
        return linearize(parse(sentence, sourceConcr), targetConcr)
    }
    
    companion object {

        // Takes a natural language sentence and parses it into its most likely syntax tree representation
        @JvmStatic
        fun parse(sentence: String, lang: Concr): Expr {
            return lang.parse("Phr", sentence).first().expr
        }

        @JvmStatic
        fun linearize(expr: Expr, lang: Concr): String {
            return lang.linearize(expr)
        }

        @JvmStatic
        fun makeMostLikelyWord(word: String, lang: Concr): String {
            return lang.lookupMorpho(word).first()?.lemma ?: ""
        }

        @JvmStatic
        fun linearizeFunction(lemma: String, lang: Concr): String {
            return linearize(Expr.readExpr(lemma), lang)
        }
    }
}
