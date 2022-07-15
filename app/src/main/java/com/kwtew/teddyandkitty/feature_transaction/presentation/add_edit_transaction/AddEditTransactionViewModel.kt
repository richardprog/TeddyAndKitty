package com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.UiText
import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.UseCaseHub
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.UiEvent
import com.kwtew.teddyandkitty.feature_transaction.util.*
import com.kwtew.teddyandkitty.feature_transaction.util.Formula.getBalanceChanges
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val useCaseHub: UseCaseHub,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var state by mutableStateOf(
        AddEditTransactionState(
            depositBalanceAmountText = "RM 0.00",
            primaryBalanceAmountText = "RM 0.00",
            secondaryBalanceAmountText = "RM 0.00",
        )
    )
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var currentMode: TransactionMode? = null
    private var currentModeCode: String? = null
    private var currentTransaction: Transaction? = null

    init {
        savedStateHandle.get<String>("mode")?.let {
            currentModeCode = it
            currentMode = when (it) {
                Constants.MODE_PT -> TransactionMode.PrimaryToThirdParty
                Constants.MODE_ST -> TransactionMode.SecondaryToThirdParty
                Constants.MODE_SP -> TransactionMode.SecondaryToPrimary
                Constants.MODE_PS -> TransactionMode.PrimaryToSecondary
                Constants.MODE_TP -> TransactionMode.ThirdPartyToPrimary
                Constants.MODE_TS -> TransactionMode.ThirdPartyToSecondary
                else -> null
            }!!
        }
        state = state.copy(
            transactionDate = Constants.dateFormatInSlashes.format(Date())
        )
        if (Preferences.currentTransactionDateInput.isEmpty()) {
            Preferences.currentTransactionDateInput = state.transactionDate
        } else {
            state = state.copy(
                transactionDate = Preferences.currentTransactionDateInput
            )
        }
        savedStateHandle.get<Int>("trx_id")?.let { trx_id ->
            if (trx_id != -1) {
                viewModelScope.launch {
                    useCaseHub.getSingleTransaction.execute(trx_id)?.also { transaction ->
                        currentTransaction = transaction
                        state = state.copy(
                            transactionDate = Constants.dateFormatInSlashes.format(Date(transaction.transactionTimestamp)),
                            venue = transaction.venue ?: "",
                            product = transaction.product ?: "",
                            thirdPartyName = transaction.thirdPartyName ?: "",
                            amount = "%.2f".format(transaction.amount),
                            remark = transaction.remark ?: ""
                        )
                    }
                }
            }

            var stringResCreate: Int? = null
            var stringResUpdate: Int? = null

            when (currentMode) {
                TransactionMode.PrimaryToThirdParty -> {
                    stringResCreate = R.string.add_edit_transaction_create_primary_to_third_party_title
                    stringResUpdate = R.string.add_edit_transaction_update_primary_to_third_party_title
                }
                TransactionMode.SecondaryToThirdParty -> {
                    stringResCreate = R.string.add_edit_transaction_create_secondary_to_third_party_title
                    stringResUpdate = R.string.add_edit_transaction_update_secondary_to_third_party_title
                }
                TransactionMode.SecondaryToPrimary -> {
                    stringResCreate = R.string.add_edit_transaction_create_secondary_to_primary_title
                    stringResUpdate = R.string.add_edit_transaction_update_secondary_to_primary_title
                }
                TransactionMode.PrimaryToSecondary -> {
                    stringResCreate = R.string.add_edit_transaction_create_primary_to_secondary_title
                    stringResUpdate = R.string.add_edit_transaction_update_primary_to_secondary_title
                }
                TransactionMode.ThirdPartyToPrimary -> {
                    stringResCreate = R.string.add_edit_transaction_create_third_party_to_primary_title
                    stringResUpdate = R.string.add_edit_transaction_update_third_party_to_primary_title
                }
                TransactionMode.ThirdPartyToSecondary -> {
                    stringResCreate = R.string.add_edit_transaction_create_third_party_to_secondary_title
                    stringResUpdate = R.string.add_edit_transaction_update_third_party_to_secondary_title
                }
                else -> null
            }!!

            state = state.copy(
                topBarTitle = UiText.StringResource(
                    resId = if (trx_id != -1) stringResUpdate!!
                                        else  stringResCreate!!
                )
            )
        }
    }

    fun onEvent(event: AddEditTransactionEvent) {
        when (event) {
            is AddEditTransactionEvent.OnEnterTransactionDate -> {
                state = state.copy(transactionDate = event.text)
                Preferences.currentTransactionDateInput = state.transactionDate
            }
            is AddEditTransactionEvent.OnEnterVenue -> {
                state = state.copy(
                    venue = event.text,
                    atLeastOneDetailError = null
                )
            }
            is AddEditTransactionEvent.OnEnterProduct -> {
                state = state.copy(
                    product = event.text,
                    atLeastOneDetailError = null
                )
            }
            is AddEditTransactionEvent.OnEnterThirdPartyName -> {
                state = state.copy(
                    thirdPartyName = event.text,
                    atLeastOneDetailError = null
                )
            }
            is AddEditTransactionEvent.OnEnterAmount -> {
                state = state.copy(
                    amount = event.text,
                    amountError = null
                )
                if (
                    state.amount.isBlank() ||
                    (state.amount.toDoubleOrNull() == null) ||
                    ((state.amount.toDoubleOrNull() ?: 0.0) <= 0.0)
                ) {
                    state = state.copy(
                        depositBalanceAmount = 0.0,
                        primaryBalanceAmount = 0.0,
                        secondaryBalanceAmount = 0.0,
                        depositBalanceAmountText = Formula.toCurrencyString(0.0, true).withThousandSeparator(),
                        primaryBalanceAmountText = Formula.toCurrencyString(0.0, true).withThousandSeparator(),
                        secondaryBalanceAmountText = Formula.toCurrencyString(0.0, true).withThousandSeparator()
                    )
                } else {
                    getBalanceChanges(
                        mode = currentMode!!,
                        amount = state.amount.toDouble(),
                        depositBalVar = {
                            state = state.copy(
                                depositBalanceAmount = it,
                                depositBalanceAmountText = Formula.toCurrencyString(it, true).withThousandSeparator(),
                            )
                        },
                        primaryBalVar = {
                            state = state.copy(
                                primaryBalanceAmount = it,
                                primaryBalanceAmountText = Formula.toCurrencyString(it, true).withThousandSeparator(),
                            )
                        },
                        secondaryBalVar = {
                            state = state.copy(
                                secondaryBalanceAmount = it,
                                secondaryBalanceAmountText = Formula.toCurrencyString(it, true).withThousandSeparator(),
                            )
                        }
                    )
                }
            }
            is AddEditTransactionEvent.OnEnterRemark -> {
                state = state.copy(
                    remark = event.text,
                    atLeastOneDetailError = null
                )
            }
            is AddEditTransactionEvent.OnClickDatePickerButton -> {

            }
            is AddEditTransactionEvent.OnClickCalculateAmount -> {
                if (state.amount.containsEither('+','-','*','/')) {
                    var number1: Double
                    var number2: Double
                    var result = 0.0

                    try {
                        with ('+') {
                            if (state.amount.contains(this,true)) {
                                number1 = state.amount.split(this)[0].toDouble()
                                number2 = state.amount.split(this)[1].toDouble()
                                result = number1 + number2
                            }
                        }
                        with ('-') {
                            if (state.amount.contains(this,true)) {
                                number1 = state.amount.split(this)[0].toDouble()
                                number2 = state.amount.split(this)[1].toDouble()
                                result = number1 - number2
                            }
                        }
                        with ('*') {
                            if (state.amount.contains(this,true)) {
                                number1 = state.amount.split(this)[0].toDouble()
                                number2 = state.amount.split(this)[1].toDouble()
                                result = number1 * number2
                            }
                        }
                        with ('/') {
                            if (state.amount.contains(this,true)) {
                                number1 = state.amount.split(this)[0].toDouble()
                                number2 = state.amount.split(this)[1].toDouble()
                                result = number1 / number2
                            }
                        }
                        state = state.copy(amount = Formula.to2DecimalString(result))
                    } catch (e: Exception) {
                        viewModelScope.launch {
                            _uiEvent.emit(UiEvent.ShowSnackbar("Number is missing!"))
                        }
                    }
                }
            }
            is AddEditTransactionEvent.OnClickSave,
            is AddEditTransactionEvent.OnDone -> {
                val amountResult = useCaseHub.validateAmount.execute(state.amount)
                val atLeastOneDetailResult = useCaseHub.checkIfAtLeastOneDetail.execute(
                    state.venue,
                    state.product,
                    state.thirdPartyName,
                    state.remark
                )

                val hasError = listOf(
                    amountResult,
                    atLeastOneDetailResult
                ).any { !it.successful }

                if (hasError) {
                    state = state.copy(
                        amountError = amountResult.errorMessage,
                        atLeastOneDetailError = atLeastOneDetailResult.errorMessage
                    )
                    return
                }

                viewModelScope.launch {
                    Constants.dateFormatInSlashes.parse(state.transactionDate)?.let { date ->
                        useCaseHub.createNewTransaction.execute(
                            with (state) {
                                currentTransaction?.let { curr ->
                                    curr.copy(
                                        transactionTimestamp = date.time,
                                        updatedTimestamp = System.currentTimeMillis(),
                                        venue = venue.ifEmpty { null },
                                        product = product.ifEmpty { null },
                                        thirdPartyName = thirdPartyName.ifEmpty { null },
                                        amount = amount.toDouble(),
                                        remark = remark.ifEmpty { null }
                                    )
                                } ?: Transaction(
                                    id = null,
                                    mode = currentModeCode!!,
                                    transactionTimestamp = date.time,
                                    createdTimestamp = System.currentTimeMillis(),
                                    updatedTimestamp = System.currentTimeMillis(),
                                    venue = venue.ifEmpty { null },
                                    product = product.ifEmpty { null },
                                    thirdPartyName = thirdPartyName.ifEmpty { null },
                                    amount = amount.toDouble(),
                                    remark = remark.ifEmpty { null }
                                )
                            }
                        )
                        if (currentTransaction == null) {
                            _uiEvent.emit(UiEvent.ShowSnackbar("New record added"))
                        } else {
                            _uiEvent.emit(UiEvent.ShowSnackbar("New record updated"))
                        }
                        _uiEvent.emit(UiEvent.NavigateUp)
                    }
                }

            }
        }
    }
}