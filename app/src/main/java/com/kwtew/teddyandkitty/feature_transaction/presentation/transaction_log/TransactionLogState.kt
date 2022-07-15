package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log

import com.kwtew.teddyandkitty.feature_transaction.domain.model.TextWithColor
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogYear

data class TransactionLogState(
    val transactionLogYearList: List<TransactionLogYear> = emptyList(),
    val depositBalDesc: TextWithColor = TextWithColor(),
    val primaryBalDesc: TextWithColor = TextWithColor(),
    val secondaryBalDesc: TextWithColor = TextWithColor(),
    val isExpanded: Boolean = false
)
