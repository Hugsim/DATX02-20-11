package org.grammaticalframework.gf

import android.util.Log

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

class GF(val sl: SmartLearning) {
    val fromLang: Concr = sl.sourceConcr!! // Implicitly creates getFromLang
    val toLang: Concr = sl.targetConcr!!   // Implicitly creates getToLang
    val grammar: PGF = sl.pgf!!            // Implicitly creates getGrammar

    fun translateWord(str: String): String {
        val answer = linearize(parse(str, fromLang), toLang)
        Log.d(TAG, "Translated \"${str}\" into \"$answer\"")

        Log.d(TAG, partOfSpeech(Word("play_1_V")))
        Log.d(TAG, generateInflectionTable(Word("apple_1_N")))

        recurse(Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (AdvVP (UseV eat_2_V) a_la_carte_Adv) today_2_Adv)))) NoVoc"))

        return answer
    }

    fun recurse(ea: Expr) {
        ea.unApp() ?. let {
            Log.d("RECURSE", it.function + " - " + it.arguments.size)

            for (e in it.arguments) {
                recurse(e)
            }
        } ?: return
    }

    fun typeOf(word: Word): String {
        return grammar.getFunctionType(word.function).category
    }

    fun partOfSpeech(word: Word): String {
        return linearize(Expr.readExpr("MkTag (Inflection${typeOf(word)} ${word.function})"), toLang)
    }

    fun generateInflectionTable(word: Word): String {
        return linearize(Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection${typeOf(word)} ${word.function}) \"\""), toLang)
    }

    fun translateSentence(sentence: String): Sentence {
        Log.d(TAG, "Translated $sentence")
        return Sentence(parse(sentence, fromLang), fromLang)
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

        @JvmStatic
        fun safeCreateWord(word: String, lang: Concr): Word {
            Log.d(TAG, "Creating word $word")
            if (lang.hasLinearization(word)) {
                return Word(word)
            } else {
                return makeMostLikelyWord(word, lang)
            }
        }
    }
}
