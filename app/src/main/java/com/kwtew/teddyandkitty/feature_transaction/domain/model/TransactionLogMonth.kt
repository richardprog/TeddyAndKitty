package com.kwtew.teddyandkitty.feature_transaction.domain.model

data class TransactionLogMonth(
    val transactionLogList: List<TransactionLog>,
    val month: Int,
    val monthDesc: String,
    val isExpanded: Boolean = true
)
