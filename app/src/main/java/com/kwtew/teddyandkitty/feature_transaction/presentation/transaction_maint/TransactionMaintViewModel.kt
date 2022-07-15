package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionDetailed
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.UseCaseHub
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionModeMapper
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.UiEvent
import com.kwtew.teddyandkitty.feature_transaction.util.Constants
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionMaintViewModel @Inject constructor(
    private val useCaseHub: UseCaseHub
): ViewModel() {
    var state by mutableStateOf(TransactionMaintState())

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var transactionToDelete: TransactionDetailed? = null

    init {
        useCaseHub.viewAllDetailedTransactions.execute().onEach { transactions ->
            state = state.copy(
                transactionDetailedList = transactions.sortedByDescending {
                        it.updatedTimestamp
                    }.map { transaction ->
                        val modeConst = TransactionModeMapper.toTransactionMode(transaction.mode)

                        val transactionDate: String = "Transaction Date: " + Constants.dateFormatInDashes.format(
                            Date(transaction.transactionTimestamp)
                        )
                        val createdDateTime: String = "Created Date & Time: " + Constants.dateTimeFormat.format(
                            Date(transaction.createdTimestamp)
                        )
                        val updatedDateTime: String = "Updated Date & Time: " + Constants.dateTimeFormat.format(
                            Date(transaction.updatedTimestamp)
                        )

                        val venueDesc = "Venue: " + (transaction.venue ?: "null")
                        val productDesc = "Product: " + (transaction.product ?: "null")
                        val thirdPartyNameDesc = "Third Party Name: " + (transaction.thirdPartyName ?: "null")

                        val amountDesc = "Amount: ${Formula.toCurrencyString(transaction.amount)}"
                        val remarkDesc = "Remark: " + (transaction.remark ?: "null")

                        TransactionDetailed(
                            transaction = transaction,
                            id = transaction.id!!,
                            mode = modeConst,
                            transactionDate = transactionDate,
                            createdDateTime = createdDateTime,
                            updatedDateTime = updatedDateTime,
                            venue = venueDesc,
                            product = productDesc,
                            thirdPartyName = thirdPartyNameDesc,
                            amount = amountDesc,
                            remark = remarkDesc,
                            isSelected = false
                        )
                    }
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: TransactionMaintEvent) {
        when (event) {
            is TransactionMaintEvent.OnToggleCheckbox -> {
                state = state.copy(
                    transactionDetailedList = state.transactionDetailedList.map { transaction ->
                        if (transaction.id == event.transaction.id) {
                            transaction.copy(isSelected = event.isChecked)
                        } else transaction
                    },
                )
                setDeleteButtonVisibility()
            }
            is TransactionMaintEvent.OnClickDeleteSelectedTransaction -> {
                state = state.copy(
                    showAlertDialogDeleteSelected = true
                )
            }
            is TransactionMaintEvent.OnConfirmDeleteSelectedTransaction -> {
                viewModelScope.launch {
                    useCaseHub.deleteMultipleTransactions.execute(
                        state.transactionDetailedList.filter { it.isSelected }.map { it.transaction }
                    )
                    delay(100L) //not practical
                    state = state.copy(
                        showAlertDialogDeleteSelected = false
                    )
                    setDeleteButtonVisibility()
                    _uiEvent.emit(UiEvent.ShowSnackbar("Delete successfully"))
                }
            }
            is TransactionMaintEvent.OnClickUpdateSingleTransaction -> {
                //to be continued
            }
            is TransactionMaintEvent.OnClickDeleteSingleTransaction -> {
                state = state.copy(
                    showAlertDialogDeleteSingle = true
                )
                transactionToDelete = event.transaction
            }
            is TransactionMaintEvent.OnConfirmDeleteSingleTransaction -> {
                viewModelScope.launch {
                    useCaseHub.deleteOneTransaction.execute(transactionToDelete!!.transaction)
                    delay(100L) //not practical
                    state = state.copy(
                        showAlertDialogDeleteSingle = false
                    )
                    setDeleteButtonVisibility()
                    transactionToDelete = null
                    _uiEvent.emit(UiEvent.ShowSnackbar("Delete successfully"))
                }
            }
            is TransactionMaintEvent.OnDismissRequestAlertDialog -> {
                state = state.copy(
                    showAlertDialogDeleteSelected = false,
                    showAlertDialogDeleteSingle = false
                )
            }
            is TransactionMaintEvent.OnClickSelectAllTransactions -> {
                state = state.copy(
                    transactionDetailedList = state.transactionDetailedList.map {
                        it.copy(
                            isSelected = true
                        )
                    }
                )
                setDeleteButtonVisibility()
            }
            is TransactionMaintEvent.OnClickDeselectAllTransactions -> {
                state = state.copy(
                    transactionDetailedList = state.transactionDetailedList.map {
                        it.copy(
                            isSelected = false
                        )
                    }
                )
                setDeleteButtonVisibility()
            }
        }
    }

    private fun setDeleteButtonVisibility() {
        state = state.copy(
            isDeleteButtonVisible = state.transactionDetailedList.any { it.isSelected }
        )
    }
}