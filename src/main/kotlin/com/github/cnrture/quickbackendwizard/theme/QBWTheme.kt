package com.github.cnrture.quickbackendwizard.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object QBWTheme {
    val colors: QBWColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Composable
fun QBWTheme(content: @Composable () -> Unit) {
    QBWTheme(
        colors = lightColors(),
        content = content,
    )
}

@Composable
private fun QBWTheme(
    colors: QBWColor = QBWTheme.colors,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        content()
    }
}