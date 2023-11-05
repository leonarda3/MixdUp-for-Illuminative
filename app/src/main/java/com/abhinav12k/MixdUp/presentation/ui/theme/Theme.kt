package com.abhinav12k.MixdUp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = lightColors(
    primary = Pink900,
    primaryVariant = Red900,
    secondary = Orange900,
    onSecondary = Color.White,
    error = Pink900,
    background = Color.White
)

@Composable
fun MixdUpTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = LightColors.background)

    MaterialTheme(
        colors = LightColors,
        typography = MixdUpTypography,
        shapes = MixdUpShapes,
        content = content
    )
}