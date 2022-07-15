package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogMonth
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogYear

@Composable
fun ListSection(
    modifier: Modifier = Modifier,
    transactionLogYearList: List<TransactionLogYear>,
    onToggleExpandedBoxYear: (TransactionLogYear) -> Unit,
    onToggleExpandedBoxMonth: (TransactionLogYear, TransactionLogMonth) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items (transactionLogYearList) { transactionLogYear ->
            YearEachCard(
                transactionLogYear = transactionLogYear,
                isExpandedYear = transactionLogYear.isExpanded,
                onToggleExpandedBoxYear = { onToggleExpandedBoxYear(transactionLogYear) },
                onToggleExpandedBoxMonth = { onToggleExpandedBoxMonth(transactionLogYear, it) }
            )
        }
    }
}