package com.kwtew.teddyandkitty.feature_transaction.util

import java.text.SimpleDateFormat

object Constants {
    val dateFormatInSlashes = SimpleDateFormat("dd/MM/yyyy")
    val dateFormatInDashes = SimpleDateFormat("dd-MM-yyyy")
    val dateTimeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a")

    const val MODE_PT = "RT"
    const val MODE_ST = "WT"
    const val MODE_SP = "WR"
    const val MODE_PS = "RW"
    const val MODE_TP = "TR"
    const val MODE_TS = "TW"
}