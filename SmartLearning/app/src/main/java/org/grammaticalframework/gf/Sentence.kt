package org.grammaticalframework.gf

import org.grammaticalframework.pgf.Concr
import org.grammaticalframework.pgf.Expr

class Sentence(val expr: Expr, val lang: Concr) {

    val stringRep: String
        get() = TODO("stringRep not done yet") // GF.parseTree(expr, lang)
}