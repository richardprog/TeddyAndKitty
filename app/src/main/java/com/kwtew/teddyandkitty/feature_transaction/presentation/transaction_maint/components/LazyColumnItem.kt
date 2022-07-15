package com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kwtew.teddyandkitty.feature_transaction.domain.model.TransactionDetailed
import com.kwtew.teddyandkitty.ui.padding

@Composable
fun LazyColumnItem(
    modifier: Modifier = Modifier,
    transaction: TransactionDetailed,
    isChecked: Boolean,
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit,
    onToggleRadioButton: (Boolean) -> Unit,
) {
    val modifierSelectedBgColor = if (isChecked) Modifier.background(MaterialTheme.colors.surface) else Modifier

    Row(
        modifier = Modifier
            .clickable {
                onToggleRadioButton(!isChecked)
            }
            .height(IntrinsicSize.Max)
            .then(modifierSelectedBgColor)
            .then(modifier),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.padding.small)
                .width(32.dp)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onToggleRadioButton,
                modifier = Modifier
                    .aspectRatio(1f)
            )
            IconButton(
                onClick = onClickUpdate,
                modifier = Modifier
                    .aspectRatio(1f)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update")
            }
            IconButton(
                onClick = onClickDelete,
                modifier = Modifier
                    .aspectRatio(1f)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
        Log.d("asdasd", "cp2 ${transaction.venue}")
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(id = transaction.mode.resId),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            )
            Row {
                Text(
                    text = transaction.venue,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = transaction.transactionDate,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
            Row {
                Text(
                    text = transaction.product,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = transaction.createdDateTime,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
            Row {
                Text(
                    text = transaction.thirdPartyName,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = transaction.updatedDateTime,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
            Row {
                Text(
                    text = transaction.remark,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = transaction.amount,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}