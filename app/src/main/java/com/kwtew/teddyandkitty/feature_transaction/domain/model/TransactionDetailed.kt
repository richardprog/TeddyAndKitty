package com.kwtew.teddyandkitty.feature_transaction.domain.model

import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode

data class TransactionDetailed(
    val transaction: Transaction,
    val id: Int,
    val mode: TransactionMode, //RT, WT, WR, RW, TR, TW
    val transactionDate: String,
    val createdDateTime: String,
    val updatedDateTime: String,
    val venue: String,
    val product: String,
    val thirdPartyName: String,
    val amount: String,
    val remark: String,
    val isSelected: Boolean = false
)
