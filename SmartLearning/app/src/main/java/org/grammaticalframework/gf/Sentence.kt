package org.grammaticalframework.gf

import android.util.Log
import org.grammaticalframework.pgf.Concr
import org.grammaticalframework.pgf.Expr
import org.grammaticalframework.pgf.ExprApplication

class Sentence(val syntaxTree: Expr, val lang: Concr) {

    val stringRep: String
        get() = GF.linearize(syntaxTree, lang)

    val listPhrases: List<String>
        get() = stringRep.split(" ")

    fun getListWords(): List<Word> {
        return TODO()
    }


}
