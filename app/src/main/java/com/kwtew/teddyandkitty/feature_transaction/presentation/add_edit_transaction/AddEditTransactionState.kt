package com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction

import androidx.compose.ui.graphics.Color
import com.kwtew.teddyandkitty.UiText

data class AddEditTransactionState(
    val transactionDate: String = "",
    val transactionDateError: String? = null,
    val venue: String = "",
    val venueError: String? = null,
    val product: String = "",
    val productError: String? = null,
    val thirdPartyName: String = "",
    val thirdPartyNameError: String? = null,
    val amount: String = "",
    val amountError: UiText? = null,
    val depositBalanceAmount: Double = 0.0,
    val depositBalanceAmountText: String,
    val primaryBalanceAmount: Double = 0.0,
    val primaryBalanceAmountText: String,
    val secondaryBalanceAmount: Double = 0.0,
    val secondaryBalanceAmountText: String,
    val remark: String = "",
    val atLeastOneDetailError: UiText? = null,
    val topBarTitle: UiText? = null
)
