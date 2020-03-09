package org.grammaticalframework.gf

import android.util.Log

import org.grammaticalframework.SmartLearning
import org.grammaticalframework.pgf.Concr
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

class GF(sl: SmartLearning) {
    var from: Concr = sl.sourceConcr
    var to: Concr = sl.targetConcr
    var grammar: PGF = sl.pgf

    fun translateWord(str: String): String {
        var answer = to.linearize(from.parse(grammar.startCat, str).firstOrNull()?.expr)
        Log.d(TAG, "Translated \"$str\" into \"$answer\"")
        return answer
    }
}