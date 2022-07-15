package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint

import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionDetailed

data class TransactionMaintState(
    val transactionDetailedList: List<TransactionDetailed> = emptyList(),
    val showAlertDialogDeleteSelected: Boolean = false,
    val showAlertDialogDeleteSingle: Boolean = false,
    val isDeleteButtonVisible: Boolean = false
)
