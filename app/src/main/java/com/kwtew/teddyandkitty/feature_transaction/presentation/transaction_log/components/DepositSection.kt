package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.util.Formula
import com.kwtew.teddyandkitty.ui.elevation
import com.kwtew.teddyandkitty.ui.padding

@Composable
fun DepositSection(
    modifier: Modifier = Modifier,
    onToggleExpandableBox: (() -> Unit)? = null,
    depositAmount: Double,
    depositBalDesc: String,
    isExpanded: Boolean,
    primaryBalDesc: String,
    primaryBalDescColor: Color,
    secondaryBalDesc: String,
    secondaryBalDescColor: Color
) {
    Column(
        modifier = modifier
    ) {
        val enableClickable = onToggleExpandableBox?.let {
            Modifier.clickable { onToggleExpandableBox() }
        } ?: Modifier
        Card(
            modifier = enableClickable,
            elevation = MaterialTheme.elevation.medium,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .padding(MaterialTheme.padding.medium)
            ) {
                val color = with (depositAmount) {
                    Formula.setComposableColorByAmount(
                        amount = this,
                        defaultColor = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = stringResource(R.string.transaction_log_deposit_balance_label),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = depositBalDesc,
                    modifier = Modifier.fillMaxWidth(),
                    color = color ?: MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h3
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
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
                    .background(color = MaterialTheme.colors.surface)
                    .padding(MaterialTheme.padding.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.transaction_log_primary_balance_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = primaryBalDesc,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        color = primaryBalDescColor,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.transaction_log_secondary_balance_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = secondaryBalDesc,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        color = secondaryBalDescColor,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}