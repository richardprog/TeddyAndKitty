package com.kwtew.teddyandkitty.feature_transaction.domain.model

import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode

data class TransactionLog (
    val transaction: Transaction,
    val mode: TransactionMode,
    val transactionDate: String,
    val remark: String = "",
    val fullDesc: String = "",
    val depositBalVar: TextWithColor,
    val primaryBalVar: TextWithColor,
    val secondaryBalVar: TextWithColor,
)