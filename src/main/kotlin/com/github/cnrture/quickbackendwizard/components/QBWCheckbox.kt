package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
fun QBWCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    label: String? = null,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .selectable(
                selected = checked,
                role = Role.Checkbox,
                onClick = { onCheckedChange(checked.not()) }
            )
            .then(
                if (checked) {
                    Modifier.background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(QBWTheme.colors.red.copy(0.7f), QBWTheme.colors.purple.copy(0.7f)),
                            tileMode = TileMode.Mirror,
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier.border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(QBWTheme.colors.red.copy(0.7f), QBWTheme.colors.purple.copy(0.7f)),
                            tileMode = TileMode.Mirror,
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false,
        ) {
            Checkbox(
                modifier = Modifier.scale(0.80f),
                checked = checked,
                onCheckedChange = onCheckedChange,
                interactionSource = NoRippleInteractionSource(),
                colors = CheckboxDefaults.colors(
                    checkedColor = QBWTheme.colors.white,
                    uncheckedColor = QBWTheme.colors.white,
                    checkmarkColor = if (checked) QBWTheme.colors.purple else QBWTheme.colors.white,
                )
            )
        }
        label?.let {
            Spacer(modifier = Modifier.size(6.dp))
            QBWText(
                text = label,
                color = QBWTheme.colors.white,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    }
}