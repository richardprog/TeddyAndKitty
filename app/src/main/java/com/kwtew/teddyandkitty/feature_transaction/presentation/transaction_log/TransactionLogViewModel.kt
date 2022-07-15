package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwtew.teddyandkitty.feature_transaction.domain.model.*
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.UseCaseHub
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionModeMapper
import com.kwtew.teddyandkitty.feature_transaction.util.Constants
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import com.kwtew.teddyandkitty.feature_transaction.util.Formula.getBalanceChanges
import com.kwtew.teddyandkitty.feature_transaction.util.withThousandSeparator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionLogViewModel @Inject constructor(
    private val useCaseHub: UseCaseHub,
) : ViewModel() {
    var state by mutableStateOf(TransactionLogState())

    init {
        useCaseHub.viewTransactionLogReport.execute().onEach { transactions ->
            var depositBal = 0.00
            var primaryBal = 0.00
            var secondaryBal = 0.00

            val transactionLogYearList = transactions.asSequence().sortedWith(
                compareByDescending<Transaction> { it.transactionTimestamp }
                    .thenByDescending { it.createdTimestamp }
            ).map { transaction ->
                val modeConst = TransactionModeMapper.toTransactionMode(transaction.mode)

                val transactionDate : String = Constants.dateFormatInDashes.format(Date(transaction.transactionTimestamp))
                val venueDesc = transaction.venue?.let { "at $it" } ?: ""
                val productDesc = transaction.product?.let { "for $it" } ?: ""
                val thirdPartyNameDesc = transaction.thirdPartyName?.let {
                    "${
                        if (modeConst is TransactionMode.ThirdPartyToPrimary
                            || modeConst is TransactionMode.ThirdPartyToSecondary) "from"
                        else "to"
                    } $it" } ?: ""
                val amountDesc = Formula.toCurrencyString(transaction.amount).withThousandSeparator()

                var fullDesc = amountDesc
                fullDesc += transaction.venue?.let {
                    "${if (fullDesc.isNotEmpty()) ", " else ""}$venueDesc"
                } ?: ""
                fullDesc += transaction.product?.let {
                    "${if (fullDesc.isNotEmpty()) ", " else ""}$productDesc"
                } ?: ""
                fullDesc += transaction.thirdPartyName?.let {
                    "${if (fullDesc.isNotEmpty()) ", " else ""}$thirdPartyNameDesc"
                } ?: ""
                fullDesc = fullDesc.replaceFirstChar { it.uppercase() }

                var depositBalVar = 0.0
                var primaryBalVar = 0.0
                var secondaryBalVar = 0.0

                getBalanceChanges(
                    mode = modeConst,
                    amount = transaction.amount,
                    depositBalVar = { depositBalVar = it } ,
                    primaryBalVar = { primaryBalVar = it },
                    secondaryBalVar = { secondaryBalVar = it }
                )

                depositBal += depositBalVar
                primaryBal += primaryBalVar
                secondaryBal += secondaryBalVar

                TransactionLog(
                    transaction = transaction,
                    mode = modeConst,
                    transactionDate = transactionDate,
                    remark = transaction.remark ?: "",
                    fullDesc = fullDesc,
                    depositBalVar = TextWithColor(
                        amount = depositBalVar,
                        text = Formula.toCurrencyString(depositBalVar, true).withThousandSeparator()
                    ),
                    primaryBalVar = TextWithColor(
                        amount = primaryBalVar,
                        text = Formula.toCurrencyString(primaryBalVar, true).withThousandSeparator()
                    ),
                    secondaryBalVar = TextWithColor(
                        amount = secondaryBalVar,
                        text = Formula.toCurrencyString(secondaryBalVar, true).withThousandSeparator()
                    )
                )
            }.groupBy {
                Calendar.getInstance().run {
                    time = Date(it.transaction.transactionTimestamp)
                    get(Calendar.YEAR)
                }
            }.map { mapYearEach ->
                TransactionLogYear(
                    transactionLogMonthList = mapYearEach.value.groupBy { trxLg ->
                        Calendar.getInstance().run {
                            time = Date(trxLg.transaction.transactionTimestamp)
                            get(Calendar.MONTH)
                        }
                    }.map { mapMonthEach ->
                        TransactionLogMonth(
                            transactionLogList = mapMonthEach.value,
                            month = mapMonthEach.key,
                            monthDesc = DateFormatSymbols().months[mapMonthEach.key]
                        )
                    }.sortedByDescending { transactionLogMonth ->
                        transactionLogMonth.month
                    },
                    year = mapYearEach.key,
                    yearDesc = mapYearEach.key.toString()
                )
            }.sortedByDescending { transactionLogYear ->
                transactionLogYear.year
            }.toList()

            state = state.copy(
                transactionLogYearList = transactionLogYearList,
                depositBalDesc = TextWithColor(
                    amount = depositBal,
                    text = Formula.toCurrencyString(depositBal).withThousandSeparator()
                ),
                primaryBalDesc = TextWithColor(
                    amount = primaryBal,
                    text = Formula.toCurrencyString(primaryBal).withThousandSeparator()
                ),
                secondaryBalDesc = TextWithColor(
                    amount = secondaryBal,
                    text = Formula.toCurrencyString(secondaryBal).withThousandSeparator()
                )
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: TransactionLogEvent) {
        when (event) {
            is TransactionLogEvent.OnToggleExpandableBox -> {
                state = state.copy(
                    isExpanded = !state.isExpanded
                )
            }
            is TransactionLogEvent.OnToggleExpandedBoxYear -> {
                state = state.copy(
                    transactionLogYearList = state.transactionLogYearList.map {
                        if (event.transactionLogYear.year == it.year) {
                            it.copy(
                                isExpanded = !it.isExpanded
                            )
                        } else { it }
                    }
                )
            }
            is TransactionLogEvent.OnToggleExpandedBoxMonth -> {
                state = state.copy(
                    transactionLogYearList = state.transactionLogYearList.map {
                        if (event.transactionLogYear.year == it.year) {
                            it.copy(
                                transactionLogMonthList = it.transactionLogMonthList.map {
                                    if (event.transactionLogMonth.month == it.month) {
                                        it.copy(
                                            isExpanded = !it.isExpanded
                                        )
                                    } else { it }
                                }
                            )
                        } else { it }
                    }
                )
            }
        }
    }
}