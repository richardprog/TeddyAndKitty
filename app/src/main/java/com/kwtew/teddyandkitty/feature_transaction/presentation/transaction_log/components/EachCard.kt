package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionLog
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import com.kwtew.teddyandkitty.ui.elevation
import com.kwtew.teddyandkitty.ui.padding

@Composable
fun EachCard(
    transactionLog: TransactionLog
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = MaterialTheme.elevation.medium,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.padding.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = transactionLog.mode.resId),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(7.5f)
                )
                Text(
                    text = transactionLog.transactionDate,
                    style = MaterialTheme.typography.body2.copy(
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Right
                    ),
                    modifier = Modifier.weight(2.5f)
                )
            }
            Text(
                text = transactionLog.fullDesc,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.transaction_log_remark_label) + " " + transactionLog.remark,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.transaction_log_db_label) + " ")
                        withStyle(
                            SpanStyle(
                                color = Formula.setComposableColorByAmount(
                                    amount = transactionLog.depositBalVar.amount,
                                    defaultColor = MaterialTheme.colors.onSurface
                                )
                            )
                        ) {
                            append(transactionLog.depositBalVar.text)
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.transaction_log_pb_label) + " ")
                        withStyle(
                            SpanStyle(
                                color = Formula.setComposableColorByAmount(
                                    amount = transactionLog.primaryBalVar.amount,
                                    defaultColor = MaterialTheme.colors.onSurface
                                )
                            )
                        ) {
                            append(transactionLog.primaryBalVar.text)
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.transaction_log_sb_label) + " ")
                        withStyle(
                            SpanStyle(
                                color = Formula.setComposableColorByAmount(
                                    amount = transactionLog.secondaryBalVar.amount,
                                    defaultColor = MaterialTheme.colors.onSurface
                                )
                            )
                        ) {
                            append(transactionLog.secondaryBalVar.text)
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}