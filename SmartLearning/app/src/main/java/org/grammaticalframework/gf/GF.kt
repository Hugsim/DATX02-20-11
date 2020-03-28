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

typealias Function = String

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

    fun recurse(ea: Expr) {
        ea.unApp() ?. let {
            Log.d("RECURSE", it.function + " - " + it.arguments.size + " - " +  linearizeWith(Expr.readExpr(it.function), sourceConcr))

            for (e in it.arguments) {
                recurse(e)
            }
        } ?: return
    }

    fun typeOfFunction(function: Function): String {
        return smartLearningInstance.grammar.getFunctionType(function).category
    }

    fun partOfSpeech(function: Function): String {
        return linearize(readExpr("MkTag (Inflection${typeOfFunction(function)} ${function})"))
    }

    fun generateInflectionTable(function: Function): String {
        return linearize(readExpr("MkDocument (NoDefinition \"\") (Inflection${typeOfFunction(function)} ${function}) \"\""))
    }

    fun translate(sentence: String): String {
        return linearize(parse(sentence))
    }

    fun linearize(expr: Expr): String {
        return linearizeWith(expr, targetConcr)
    }

    fun parse(sentence: String): Expr {
        return parseWith(sentence, sourceConcr)
    }

    fun possibleFunctions(word: String): List<Function> {
        return targetConcr.lookupMorpho(word).map { a -> a.lemma }
    }

    fun allLinearizations(expr: Expr): Iterable<String> {
        return targetConcr.linearizeAll(expr)
    }

    fun hasLinearization(function: Function): Boolean {
        return hasLinearizationIn(function, targetConcr)
    }

    fun tabularLinearize(expr: Expr): Iterable<Map.Entry<String, Function>> {
        return targetConcr.tabularLinearize(expr).asIterable()
    }

    companion object {
        @JvmStatic
        fun hasLinearizationIn(function: Function, lang: Concr): Boolean {
            return lang.hasLinearization(function)
        }

        // Takes a natural language sentence and parses it into its most likely syntax tree representation
        @JvmStatic
        fun parseWith(sentence: String, lang: Concr): Expr {
            return lang.parse("Phr", sentence).first().expr
        }

        @JvmStatic
        fun linearizeWith(expr: Expr, lang: Concr): String {
            return lang.linearize(expr)
        }

        @JvmStatic
        fun mostLikelyFunction(word: String, lang: Concr): Function {
            return lang.lookupMorpho(word).first()?.lemma ?: ""
        }

        @JvmStatic
        fun linearizeFunction(function: Function, lang: Concr): String {
            return linearizeWith(readExpr(function), lang)
        }

        @JvmStatic
        fun readExpr(expr: String): Expr {
            return Expr.readExpr(expr)
        }
    }
}
