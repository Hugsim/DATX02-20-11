package org.grammaticalframework.gf

import android.util.Log

import org.grammaticalframework.SmartLearning
import org.grammaticalframework.pgf.Concr
import org.grammaticalframework.pgf.Expr
import org.grammaticalframework.pgf.PGF
import java.lang.IllegalArgumentException

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

class GF(val sl: SmartLearning) {
    val from: Concr = sl.sourceConcr!!
    val to: Concr = sl.targetConcr!!
    val grammar: PGF = sl.pgf!!

    fun translateWord(str: String): String {
        val answer = linearize(parse(str, from), to)
        Log.d(TAG, "Translated \"${str}\" into \"$answer\"")

        Log.d(TAG, partOfSpeech(Word("play_1_V")))
        Log.d(TAG, generateInflectionTable(Word("apple_1_N")))

        //recurse(Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (AdvVP (UseV eat_2_V) a_la_carte_Adv) today_2_Adv)))) NoVoc"))

        return answer
    }

    fun recurse(ea: Expr) {
        ea.unApp() ?. let {
            Log.d("RECURSE", it.function + " - " + it.arguments.size + " - " +  linearize(Expr.readExpr(it.function), from))

            for (e in it.arguments) {
                recurse(e)
            }
        } ?: return
    }

    fun typeOf(word: Word): String {
        return grammar.getFunctionType(word.function).category
    }

    fun partOfSpeech(word: Word): String {
        return linearize(Expr.readExpr("MkTag (Inflection${typeOf(word)} ${word.function})"), to)
    }

    fun generateInflectionTable(word: Word): String {
        return linearize(Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection${typeOf(word)} ${word.function}) \"\""), to)
    }

    fun translateSentence(sentence: String): Sentence {
        Log.d(TAG, "Translated $sentence")
        return Sentence(parse(sentence, from), from)
    }
    
    companion object {
        @JvmStatic
        fun parse(sentence: String, lang: Concr): Expr {
            return lang.parse("Phr", sentence).first().expr
        }

        @JvmStatic
        fun linearize(expr: Expr, lang: Concr): String {
            return lang.linearize(expr)
        }

        @JvmStatic
        fun makeMostLikelyWord(word: String, lang: Concr): Word {
            return Word(lang.lookupMorpho(word).first()?.lemma ?: "")
        }
    }
}
