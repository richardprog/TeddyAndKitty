package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components.DepositSection
import com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components.ListSection
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import com.kwtew.teddyandkitty.ui.WindowInfo
import com.kwtew.teddyandkitty.ui.padding
import com.kwtew.teddyandkitty.ui.rememberWindowInfo
import com.kwtew.teddyandkitty.ui.spacing

@Composable
fun TransactionLogScreen(
    navController: NavController,
    viewModel: TransactionLogViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.transaction_log_title)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Up"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val windowInfo = rememberWindowInfo()

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                Column(
                    modifier = Modifier
                        .padding(MaterialTheme.padding.medium)
                        .fillMaxSize()
                ) {
                    DepositSection(
                        modifier = Modifier,
                        onToggleExpandableBox = { viewModel.onEvent(TransactionLogEvent.OnToggleExpandableBox) },
                        depositAmount = viewModel.state.depositBalDesc.amount,
                        depositBalDesc = viewModel.state.depositBalDesc.text,
                        isExpanded = viewModel.state.isExpanded,
                        primaryBalDesc = viewModel.state.primaryBalDesc.text,
                        primaryBalDescColor = Formula.setComposableColorByAmount(
                            viewModel.state.primaryBalDesc.amount,
                            MaterialTheme.colors.onSurface
                        ),
                        secondaryBalDesc = viewModel.state.secondaryBalDesc.text,
                        secondaryBalDescColor = Formula.setComposableColorByAmount(
                            viewModel.state.secondaryBalDesc.amount,
                            MaterialTheme.colors.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    ListSection(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        transactionLogYearList = viewModel.state.transactionLogYearList,
                        onToggleExpandedBoxYear = {
                            viewModel.onEvent(TransactionLogEvent.OnToggleExpandedBoxYear(it))
                        },
                        onToggleExpandedBoxMonth = { transactionLogYear, transactionLogMonth ->
                            viewModel.onEvent(
                                TransactionLogEvent.OnToggleExpandedBoxMonth(
                                    transactionLogMonth = transactionLogMonth,
                                    transactionLogYear = transactionLogYear
                                )
                            )
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .padding(MaterialTheme.padding.medium)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.Top
                ) {
                    DepositSection(
                        modifier = Modifier.weight(4f),
                        depositAmount = viewModel.state.depositBalDesc.amount,
                        depositBalDesc = viewModel.state.depositBalDesc.text,
                        isExpanded = true,
                        primaryBalDesc = viewModel.state.primaryBalDesc.text,
                        primaryBalDescColor = Formula.setComposableColorByAmount(
                            viewModel.state.primaryBalDesc.amount,
                            MaterialTheme.colors.onSurface
                        ),
                        secondaryBalDesc = viewModel.state.secondaryBalDesc.text,
                        secondaryBalDescColor = Formula.setComposableColorByAmount(
                            viewModel.state.secondaryBalDesc.amount,
                            MaterialTheme.colors.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    ListSection(
                        modifier = Modifier
                            .weight(6f)
                            .fillMaxHeight(),
                        transactionLogYearList = viewModel.state.transactionLogYearList,
                        onToggleExpandedBoxYear = {
                            viewModel.onEvent(TransactionLogEvent.OnToggleExpandedBoxYear(it))
                        },
                        onToggleExpandedBoxMonth = { transactionLogYear, transactionLogMonth ->
                            viewModel.onEvent(
                                TransactionLogEvent.OnToggleExpandedBoxMonth(
                                    transactionLogMonth = transactionLogMonth,
                                    transactionLogYear = transactionLogYear
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
