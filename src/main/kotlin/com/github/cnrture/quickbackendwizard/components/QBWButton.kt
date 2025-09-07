package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.cnrture.quickbackendwizard.common.NoRippleInteractionSource
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun QBWButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = QBWTheme.colors.white,
        ),
        interactionSource = NoRippleInteractionSource(),
        onClick = onClick,
        content = {
            QBWText(
                text = text,
                color = QBWTheme.colors.white,
            )
        },
    )
}

@Composable
fun QBWOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor.copy(alpha = 0.1f),
            contentColor = QBWTheme.colors.white,
        ),
        border = BorderStroke(
            width = 2.dp,
            color = backgroundColor,
        ),
        onClick = onClick,
        content = {
            QBWText(
                text = text,
                color = QBWTheme.colors.white,
            )
        },
    )
}