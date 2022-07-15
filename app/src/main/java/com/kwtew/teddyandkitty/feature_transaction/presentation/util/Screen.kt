package com.kwtew.teddyandkitty.feature_transaction.presentation.util

import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode


sealed class Screen(val route: String) {
    object MainMenuScreen : Screen("main_menu_screen")
    data class AddTransactionScreen(val transactionMode: TransactionMode? = null)
        : Screen("add_edit_transaction_screen" + (transactionMode?.let { "?mode=${transactionMode.mode}" } ?: ""))
    data class EditTransactionScreen(
        val transactionMode: TransactionMode,
        val id: Int
    )
        : Screen("add_edit_transaction_screen?mode=${transactionMode.mode}&trx_id=${id}")
    object TransactionLogScreen : Screen("transaction_log_report_screen")
    object TransactionMaintScreen : Screen("transaction_maint_screen")
}


