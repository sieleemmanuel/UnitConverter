package com.siele.unitconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val DarkColorPalette = darkColors(
    primary = Indigo900,
    primaryVariant = Indigo900,
    secondary = Teal200,
    onPrimary = Color.White,
    onSecondary = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Indigo900,
    primaryVariant = Indigo900,
    secondary = Teal200,
    background = Indigo100,
    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    /*val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0xFF1A237E),
            darkIcons = false
        )
    }*/

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
@Preview(showSystemUi = true)
@Composable
fun PreviewTheme() {
    ComposerTheme() {

    }
}