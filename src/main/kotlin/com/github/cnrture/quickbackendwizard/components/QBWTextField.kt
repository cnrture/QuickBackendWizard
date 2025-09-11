package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun QBWTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    color: Color = QBWTheme.colors.white,
    textStyle: TextStyle = TextStyle.Default,
    isSingleLine: Boolean = true,
    isEnabled: Boolean = true,
) {
    BasicTextField(
        modifier = modifier
            .heightIn(min = 44.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = QBWTheme.colors.white,
                shape = RoundedCornerShape(8.dp)
            ),
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle.copy(
            color = QBWTheme.colors.white,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        ),
        singleLine = isSingleLine,
        cursorBrush = SolidColor(value = color),
        enabled = isEnabled,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty() && placeholder != null) {
                    QBWText(
                        text = placeholder,
                        color = QBWTheme.colors.hintGray,
                        style = textStyle.copy(
                            fontSize = 14.sp,
                        ),
                    )
                }
                innerTextField()
            }
        },
    )
}
