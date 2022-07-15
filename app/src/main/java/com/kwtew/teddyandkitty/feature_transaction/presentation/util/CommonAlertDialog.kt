package com.kwtew.teddyandkitty.feature_transaction.presentation.util

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CommonAlertDialog(
    showAlertDialog: Boolean,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(title)
            },
            text = {
                Text(text)
            },
            confirmButton = {
                Button(
                    onClick = onConfirm
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("No")
                }
            }
        )
    }
}