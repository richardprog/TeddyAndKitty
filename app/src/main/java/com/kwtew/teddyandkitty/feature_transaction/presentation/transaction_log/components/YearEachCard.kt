package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogMonth
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLogYear
import com.kwtew.teddyandkitty.ui.elevation
import com.kwtew.teddyandkitty.ui.padding

@Composable
fun YearEachCard(
    transactionLogYear: TransactionLogYear,
    isExpandedYear: Boolean,
    onToggleExpandedBoxYear: () -> Unit,
    onToggleExpandedBoxMonth: (TransactionLogMonth) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = MaterialTheme.elevation.medium
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = transactionLogYear.yearDesc,
                modifier = Modifier
                    .clickable {
                        onToggleExpandedBoxYear()
                    }
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
                    .padding(MaterialTheme.padding.medium),
                fontWeight = FontWeight.Bold
            )
            AnimatedVisibility(
                visible = isExpandedYear,
                enter = expandVertically(
                    expandFrom = Alignment.Bottom,
                    animationSpec = tween(
                        durationMillis = 100
                    )
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Bottom,
                    animationSpec = tween(
                        durationMillis = 100
                    )
                ),
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .padding(MaterialTheme.padding.medium)
                ) {
                    transactionLogYear.transactionLogMonthList.forEach { transactionLogMonth ->
                        MonthEachCard(
                            transactionLogMonth = transactionLogMonth,
                            isExpandedMonth = transactionLogMonth.isExpanded,
                            onToggleExpandedBoxMonth = {
                                onToggleExpandedBoxMonth(transactionLogMonth)
                            }
                        )
                    }
                }
            }
        }
    }
}