package com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction.components.OutlinedTextFieldWithTrailingIcon
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.UiEvent
import com.kwtew.teddyandkitty.feature_transaction.util.Constants
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import com.kwtew.teddyandkitty.ui.padding
import com.kwtew.teddyandkitty.ui.spacing
import java.util.*

@Composable
fun AddEditTransactionScreen(
    navController: NavController,
    onShowSnackbar: (String) -> Unit,
    viewModel: AddEditTransactionViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    onShowSnackbar(it.message)
                }
                is UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.state.topBarTitle?.asString() ?: "") },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTransactionEvent.OnClickSave)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.add_edit_transaction_save_button)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val focusManager = LocalFocusManager.current

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.padding.medium)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_transaction_date_label),
                            value = viewModel.state.transactionDate,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterTransactionDate(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_required_placeholder),
                            onNextFocus = {focusManager.moveFocus(FocusDirection.Down)},
                            trailingIcon = Icons.Default.DateRange,
                            onClickTrailingIcon = {
                                Constants.dateFormatInSlashes.parse(viewModel.state.transactionDate)?.let { date ->
                                    val cal = Calendar.getInstance()
                                    cal.time = date

                                    val datePickerDialog = DatePickerDialog(
                                        context,
                                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                            viewModel.onEvent(AddEditTransactionEvent.OnEnterTransactionDate(
                                                "${"%02d".format(dayOfMonth)}/${"%02d".format(month+1)}/$year"
                                            ))
                                        },
                                        cal.get(Calendar.YEAR),
                                        cal.get(Calendar.MONTH),
                                        cal.get(Calendar.DAY_OF_MONTH)
                                    )
                                    datePickerDialog.show()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_venue_label),
                            value = viewModel.state.venue,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterVenue(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_optional_placeholder),
                            focusRequester = Modifier.focusRequester(focusRequester),
                            onNextFocus = {focusManager.moveFocus(FocusDirection.Down)},
                            isError = viewModel.state.atLeastOneDetailError != null
                        )
                        Text(
                            text = viewModel.state.atLeastOneDetailError?.asString() ?: "",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_product_label),
                            value = viewModel.state.product,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterProduct(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_optional_placeholder),
                            onNextFocus = {focusManager.moveFocus(FocusDirection.Down)},
                            isError = viewModel.state.atLeastOneDetailError != null
                        )
                        Text(
                            text = viewModel.state.atLeastOneDetailError?.asString() ?: "",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_third_party_name_label),
                            value = viewModel.state.thirdPartyName,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterThirdPartyName(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_optional_placeholder),
                            onNextFocus = {focusManager.moveFocus(FocusDirection.Down)},
                            isError = viewModel.state.atLeastOneDetailError != null
                        )
                        Text(
                            text = viewModel.state.atLeastOneDetailError?.asString() ?: "",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_amount_label),
                            value = viewModel.state.amount,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterAmount(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_required_placeholder),
                            onNextFocus = {focusManager.moveFocus(FocusDirection.Down)},
                            trailingIcon = Icons.Default.Calculate,
                            onClickTrailingIcon = { viewModel.onEvent(AddEditTransactionEvent.OnClickCalculateAmount) },
                            isError = viewModel.state.amountError != null
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(6f)
                                    .background(MaterialTheme.colors.surface)
                                    .padding(MaterialTheme.padding.medium),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Deposit Balance: ${viewModel.state.depositBalanceAmountText}",
                                    color = Formula.setComposableColorByAmount(
                                        amount = viewModel.state.depositBalanceAmount,
                                        defaultColor = MaterialTheme.colors.onBackground
                                    )
                                )
                                Text(
                                    text = "Richard Balance: ${viewModel.state.primaryBalanceAmountText}",
                                    color = Formula.setComposableColorByAmount(
                                        amount = viewModel.state.primaryBalanceAmount,
                                        defaultColor = MaterialTheme.colors.onBackground
                                    )
                                )
                                Text(
                                    text = "Wanyin Balance: ${viewModel.state.secondaryBalanceAmountText}",
                                    color = Formula.setComposableColorByAmount(
                                        amount = viewModel.state.secondaryBalanceAmount,
                                        defaultColor = MaterialTheme.colors.onBackground
                                    )
                                )
                            }
                            Text(
                                text = viewModel.state.amountError?.asString() ?: "",
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.weight(4f),
                                textAlign = TextAlign.Right
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        OutlinedTextFieldWithTrailingIcon(
                            label = stringResource(R.string.add_edit_transaction_remark_label),
                            value = viewModel.state.remark,
                            onEnterValue = { viewModel.onEvent(AddEditTransactionEvent.OnEnterRemark(it)) },
                            placeholder = stringResource(R.string.add_edit_transaction_optional_placeholder),
                            onDone = { viewModel.onEvent(AddEditTransactionEvent.OnDone) },
                            isError = viewModel.state.atLeastOneDetailError != null
                        )
                        Text(
                            text = viewModel.state.atLeastOneDetailError?.asString() ?: "",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }
    }
}