package com.kwtew.teddyandkitty.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Padding(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 32.dp
)

val LocalPadding = compositionLocalOf { Padding() }

val MaterialTheme.padding: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current