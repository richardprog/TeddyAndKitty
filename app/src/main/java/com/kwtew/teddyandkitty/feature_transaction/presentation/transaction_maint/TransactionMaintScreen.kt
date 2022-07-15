package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint.components.LazyColumnItem
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.CommonAlertDialog
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.Screen
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.UiEvent
import com.kwtew.teddyandkitty.ui.padding
import com.kwtew.teddyandkitty.ui.spacing

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun TransactionMaintScreen(
    navController: NavController,
    onShowSnackbar: (String) -> Unit,
    viewModel: TransactionMaintViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    onShowSnackbar(it.message)
                }
                is UiEvent.ShowDatePicker -> {

                }
                UiEvent.NavigateUp -> {

                }
            }
        }
    }
    CommonAlertDialog(
        showAlertDialog = viewModel.state.showAlertDialogDeleteSelected,
        title = stringResource(R.string.transaction_maint_dialog_delete_selected_title),
        text = stringResource(R.string.transaction_maint_dialog_proceed_desc),
        onConfirm = {viewModel.onEvent(TransactionMaintEvent.OnConfirmDeleteSelectedTransaction)},
        onDismiss = {viewModel.onEvent(TransactionMaintEvent.OnDismissRequestAlertDialog)}
    )
    CommonAlertDialog(
        showAlertDialog = viewModel.state.showAlertDialogDeleteSingle,
        title = stringResource(R.string.transaction_maint_dialog_delete_single_title),
        text = stringResource(R.string.transaction_maint_dialog_proceed_desc),
        onConfirm = {viewModel.onEvent(TransactionMaintEvent.OnConfirmDeleteSingleTransaction)},
        onDismiss = {viewModel.onEvent(TransactionMaintEvent.OnDismissRequestAlertDialog)}
    )

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.transaction_maint_title)) },
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
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(TransactionMaintEvent.OnClickDeselectAllTransactions) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Deselect All"
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(TransactionMaintEvent.OnClickSelectAllTransactions) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Select All"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = viewModel.state.isDeleteButtonVisible,
                enter = scaleIn(tween(100)),
                exit = scaleOut(tween(100))
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(TransactionMaintEvent.OnClickDeleteSelectedTransaction)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.transaction_maint_delete_button)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.state.transactionDetailedList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    itemsIndexed (
                        items = viewModel.state.transactionDetailedList,
                        key = {_, item -> item.id}
                    ) { index, transaction ->
                        if (index > 0) {
                            Divider(thickness = MaterialTheme.spacing.small)
                        }
                        LazyColumnItem(
                            transaction = transaction,
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.padding.small)
                                .animateItemPlacement(
                                    tween(durationMillis = 200)
                                )
                                .fillMaxWidth(),
                            isChecked = transaction.isSelected,
                            onToggleRadioButton = {
                                viewModel.onEvent(TransactionMaintEvent.OnToggleCheckbox(
                                    transaction = transaction,
                                    isChecked = it
                                ))
                            },
                            onClickUpdate = {
                                navController.navigate(
                                    route = Screen.EditTransactionScreen(transaction.mode, transaction.id).route
                                )
                            },
                            onClickDelete = {
                                viewModel.onEvent(TransactionMaintEvent.OnClickDeleteSingleTransaction(transaction))
                            }
                        )
                        Log.d("asdasd", "cp1 ${transaction.venue}")
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.transaction_maint_no_record_info)
                )
            }
        }
    }
}