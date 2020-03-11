package org.grammaticalframework.gf



class Word(val function: String) {
    init {
        throw IllegalArgumentException("Please do not use the Word constructor directly, please use GF.createWord() instead.")
    }
}