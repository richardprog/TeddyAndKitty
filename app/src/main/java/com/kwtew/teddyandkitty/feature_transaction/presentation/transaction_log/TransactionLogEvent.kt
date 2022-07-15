package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log

import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogMonth
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogYear

sealed class TransactionLogEvent {
    object OnToggleExpandableBox: TransactionLogEvent()
    data class OnToggleExpandedBoxYear(val transactionLogYear: TransactionLogYear): TransactionLogEvent()
    data class OnToggleExpandedBoxMonth(
        val transactionLogMonth: TransactionLogMonth,
        val transactionLogYear: TransactionLogYear
    ): TransactionLogEvent()
}
