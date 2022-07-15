package com.kwtew.teddyandkitty.feature_transaction.util

fun String.withThousandSeparator(): String {
    var newString = StringBuffer(this)
    val hasDecimalPoint = this.contains('.')

    if (hasDecimalPoint) {
        val decimalPos = this.indexOfLast { it == '.' }
        var digitsCount = 0
        for (i in (decimalPos-1) downTo 0 ) {
            if (this[i].isDigit()) {
                if (digitsCount >= 3) {
                    newString.insert(i+1, ',')
                    digitsCount = 1
                } else {
                    digitsCount++
                }
            } else {
                break
            }
        }
    } else {
        val decimalPos = this.indexOfLast { it.isDigit() }
        var digitsCount = 0
        for (i in (decimalPos) downTo 0 ) {
            if (this[i].isDigit()) {
                if (digitsCount >= 3) {
                    newString.insert(i+1, ',')
                    digitsCount = 1
                } else {
                    digitsCount++
                }
            } else {
                break
            }
        }
    }

    return newString.toString()
}

fun String.containsEither(vararg char: Char): Boolean {
    char.forEach { each ->
        if (this.contains(each)) {
            return true
        }
    }
    return false
}