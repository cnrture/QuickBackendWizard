package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

enum class QBWActionCardType { SMALL, MEDIUM, LARGE }

@Composable
fun QBWActionCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: ImageVector? = null,
    actionColor: Color,
    isTextVisible: Boolean = true,
    type: QBWActionCardType = QBWActionCardType.LARGE,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    val fontSize = when (type) {
        QBWActionCardType.SMALL -> 14.sp
        QBWActionCardType.MEDIUM -> 16.sp
        QBWActionCardType.LARGE -> 20.sp
    }
    val iconBoxSize = when (type) {
        QBWActionCardType.SMALL -> 24.dp
        QBWActionCardType.MEDIUM -> 28.dp
        QBWActionCardType.LARGE -> 32.dp
    }
    val iconSize = when (type) {
        QBWActionCardType.SMALL -> 16.dp
        QBWActionCardType.MEDIUM -> 20.dp
        QBWActionCardType.LARGE -> 24.dp
    }
    val borderSize = when (type) {
        QBWActionCardType.SMALL -> 1.dp
        QBWActionCardType.MEDIUM -> 2.dp
        QBWActionCardType.LARGE -> 3.dp
    }
    val padding = when (type) {
        QBWActionCardType.SMALL -> 8.dp
        QBWActionCardType.MEDIUM -> 12.dp
        QBWActionCardType.LARGE -> 16.dp
    }
    Row(
        modifier = modifier
            .background(
                color = if (isEnabled) QBWTheme.colors.gray else QBWTheme.colors.lightGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = borderSize,
                color = if (isEnabled) actionColor else QBWTheme.colors.lightGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (isEnabled) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        icon?.let {
            if (type == QBWActionCardType.LARGE) {
                Box(
                    modifier = Modifier
                        .size(iconBoxSize)
                        .clip(RoundedCornerShape(8.dp))
                        .background(actionColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = QBWTheme.colors.white,
                        modifier = Modifier.size(iconSize)
                    )
                }
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = actionColor,
                    modifier = Modifier.size(iconSize)
                )
            }
        }

        if (icon != null && title != null && isTextVisible) {
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (isTextVisible) {
            title?.let {
                QBWText(
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = fontSize,
                        color = QBWTheme.colors.white,
                    ),
                )
            }
        }
    }
}