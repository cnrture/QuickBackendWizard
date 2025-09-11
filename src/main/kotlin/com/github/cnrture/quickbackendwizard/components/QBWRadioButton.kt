package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.common.NoRippleInteractionSource
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QBWRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    isEnabled: Boolean = true,
    description: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .then(
                if (isEnabled) {
                    Modifier.selectable(
                        selected = selected,
                        role = Role.RadioButton,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (selected) {
                    Modifier.background(
                        Brush.horizontalGradient(
                            colors = listOf(QBWTheme.colors.red, QBWTheme.colors.purple),
                            tileMode = TileMode.Mirror,
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false,
        ) {
            RadioButton(
                colors = RadioButtonDefaults.colors(
                    selectedColor = QBWTheme.colors.white,
                    unselectedColor = QBWTheme.colors.white,
                    disabledColor = QBWTheme.colors.white,
                ),
                interactionSource = NoRippleInteractionSource(),
                enabled = isEnabled,
                selected = selected,
                onClick = onClick,
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Column {
            QBWText(
                text = text,
                color = QBWTheme.colors.white,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            if (description != null) {
                Spacer(modifier = Modifier.size(4.dp))
                QBWText(
                    text = description,
                    color = QBWTheme.colors.white,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
        }
    }
}