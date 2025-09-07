package com.github.cnrture.quickbackendwizard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun QBWTabRow(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    isSelected: Boolean,
    onTabSelected: () -> Unit,
) {
    Card(
        modifier = Modifier.clickable { onTabSelected() }.then(modifier),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (isSelected) color else QBWTheme.colors.gray,
        elevation = 0.dp
    ) {
        QBWText(
            text = text,
            color = if (isSelected) QBWTheme.colors.white else QBWTheme.colors.lightGray,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}