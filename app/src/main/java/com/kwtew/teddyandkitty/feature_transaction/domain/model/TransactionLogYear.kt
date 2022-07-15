package com.kwtew.teddyandkitty.feature_transaction.domain.model

data class TransactionLogYear(
    val transactionLogMonthList: List<TransactionLogMonth>,
    val year: Int,
    val yearDesc: String,
    val isExpanded: Boolean = true
)
