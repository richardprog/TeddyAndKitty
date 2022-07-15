package com.kwtew.teddyandkitty.feature_transaction.presentation.main_menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kwtew.teddyandkitty.ui.padding
import com.kwtew.teddyandkitty.ui.theme_comp.ThemedButton

@Composable
fun ButtonEachOption(
    text: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ThemedButton(
        onClick = onClick,
        modifier = modifier,
    ) { contentColor ->
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Left
            )
            Text(
                text = description,
                color = contentColor,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Left
            )
        }
    }
}