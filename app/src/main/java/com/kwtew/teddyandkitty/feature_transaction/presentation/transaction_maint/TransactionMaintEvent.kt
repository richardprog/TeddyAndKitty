package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint

import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionDetailed

sealed class TransactionMaintEvent {
    data class OnToggleCheckbox(
        val transaction: TransactionDetailed,
        val isChecked: Boolean
    ): TransactionMaintEvent()
    data class OnClickUpdateSingleTransaction(val transaction: TransactionDetailed): TransactionMaintEvent()
    object OnClickDeleteSelectedTransaction: TransactionMaintEvent()
    object OnConfirmDeleteSelectedTransaction: TransactionMaintEvent()
    data class OnClickDeleteSingleTransaction(val transaction: TransactionDetailed): TransactionMaintEvent()
    object OnConfirmDeleteSingleTransaction: TransactionMaintEvent()
    object OnDismissRequestAlertDialog: TransactionMaintEvent()
    object OnClickSelectAllTransactions: TransactionMaintEvent()
    object OnClickDeselectAllTransactions: TransactionMaintEvent()
}
