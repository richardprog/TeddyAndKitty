package com.kwtew.teddyandkitty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.kwtew.teddyandkitty.ui.*


private val LightColorPalette = lightColors(
    primary = Color(0xFF7dbcf0),
    primaryVariant = Color(0xFF4aa3ed),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC5),
    secondaryVariant = Color(0xFF018786),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color(0xffeeeeee),
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

private val DarkColorPalette = darkColors(
    primary = Color(0xff003c9c),
    primaryVariant = Color(0xff01245c),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    secondaryVariant = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color(0xff262626),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun TeddyAndKittyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalPadding provides Padding(),
        LocalElevation provides Elevation()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}