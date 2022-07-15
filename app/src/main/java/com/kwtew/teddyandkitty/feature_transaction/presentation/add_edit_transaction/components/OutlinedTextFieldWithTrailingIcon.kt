package com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun OutlinedTextFieldWithTrailingIcon(
    label : String,
    modifier : Modifier = Modifier,
    focusRequester: Modifier = Modifier,
    value : String,
    onEnterValue : (String) -> Unit,
    placeholder: String,
    onNextFocus: (KeyboardActionScope.() -> Unit)? = null,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    useNumPad: Boolean = false,
    isError: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        var keyboardActions: KeyboardActions = KeyboardActions.Default
        var keyboardOptions: KeyboardOptions = KeyboardOptions.Default

        if (useNumPad) {
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        } else {
            onNextFocus?.let {
                keyboardActions = KeyboardActions(onNext = onNextFocus)
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            } ?: onDone.let {
                keyboardActions = KeyboardActions(onDone = onDone)
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = { onEnterValue(it) },
            singleLine = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                if (trailingIcon != null && onClickTrailingIcon != null) {
                    IconButton(onClick = onClickTrailingIcon) {
                        Icon(imageVector = trailingIcon, contentDescription = "")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(focusRequester),
            placeholder = { Text(text = placeholder) },
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            isError = isError
        )
    }
}