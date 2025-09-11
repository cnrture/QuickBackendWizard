package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWRadioButton
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DatabasesContent(moduleBuilder: QBWSpringBootModuleBuilder) {
    var selectedDatabase by remember { mutableStateOf(DatabaseType.ALL.first().type) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QBWTheme.colors.gray)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QBWText(
            text = "Database",
            color = QBWTheme.colors.white,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        QBWText(
            text = "Select the database you want to use in your project.",
            color = QBWTheme.colors.lightGray,
            style = TextStyle(fontSize = 14.sp),
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = QBWTheme.colors.lightGray,
        )
        DatabaseType.ALL.forEach {
            DatabaseItem(
                name = it.name,
                description = it.description,
                type = it.type,
                selectedType = selectedDatabase,
                onSelect = { type ->
                    selectedDatabase = type
                    moduleBuilder.selectedDatabase = type
                },
            )
        }
    }
}

@Composable
private fun DatabaseItem(
    name: String,
    description: String,
    type: String,
    selectedType: String,
    onSelect: (String) -> Unit,
) {
    QBWRadioButton(
        text = name,
        description = description,
        selected = selectedType == type,
        onClick = { onSelect(type) },
    )
}