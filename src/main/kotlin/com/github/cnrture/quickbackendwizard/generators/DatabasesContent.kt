package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWRadioButton
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DatabasesContent(moduleBuilder: QBWSpringBootModuleBuilder) {
    var selectedDatabase by remember { mutableStateOf(DatabaseType.ALL.first().type) }
    var isDatabaseEnabled by remember { mutableStateOf(false) }
    var dbName by remember { mutableStateOf("") }
    var dbUsername by remember { mutableStateOf("") }
    var dbPassword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QBWText(
                text = "Database",
                color = QBWTheme.colors.white,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Switch(
                checked = isDatabaseEnabled,
                onCheckedChange = {
                    isDatabaseEnabled = it
                    if (!it) {
                        selectedDatabase = DatabaseType.ALL.first().type
                        moduleBuilder.selectedDatabase = DatabaseType.ALL.first().type
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = QBWTheme.colors.red,
                    uncheckedThumbColor = QBWTheme.colors.lightGray,
                    checkedTrackColor = QBWTheme.colors.red,
                    uncheckedTrackColor = QBWTheme.colors.lightGray.copy(alpha = 0.5f),
                ),
            )
        }
        QBWText(
            text = "Select the database you want to use in your project.",
            color = QBWTheme.colors.lightGray,
            style = TextStyle(fontSize = 14.sp),
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = QBWTheme.colors.lightGray,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                QBWText(
                    text = "Database Type",
                    color = QBWTheme.colors.red,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 2,
                ) {
                    DatabaseType.ALL.forEach { it ->
                        DatabaseItem(
                            modifier = Modifier.weight(1f),
                            name = it.name,
                            description = it.description,
                            type = it.type,
                            selectedType = selectedDatabase,
                            isEnabled = isDatabaseEnabled,
                            onSelect = { type ->
                                selectedDatabase = type
                                moduleBuilder.selectedDatabase = type
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        QBWText(
                            text = "Database Name",
                            color = QBWTheme.colors.red,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        QBWTextField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "(e.g., testdb)",
                            value = dbName,
                            isEnabled = isDatabaseEnabled,
                            onValueChange = {
                                dbName = it
                                moduleBuilder.dbName = it
                            },
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        QBWText(
                            text = "Database Username",
                            color = QBWTheme.colors.red,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        QBWTextField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "(e.g., root)",
                            value = dbUsername,
                            isEnabled = isDatabaseEnabled,
                            onValueChange = {
                                dbUsername = it
                                moduleBuilder.dbUsername = it
                            },
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        QBWText(
                            text = "Database Password",
                            color = QBWTheme.colors.red,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        QBWTextField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "(e.g., password)",
                            value = dbPassword,
                            isEnabled = isDatabaseEnabled,
                            onValueChange = {
                                dbPassword = it
                                moduleBuilder.dbPassword = it
                            },
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = if (isDatabaseEnabled) {
                            QBWTheme.colors.gray.copy(alpha = 0.0f)
                        } else {
                            QBWTheme.colors.gray.copy(alpha = 0.6f)
                        },
                    )
            )
        }
    }
}

@Composable
private fun DatabaseItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    type: String,
    selectedType: String,
    isEnabled: Boolean,
    onSelect: (String) -> Unit,
) {
    QBWRadioButton(
        modifier = modifier,
        text = name,
        description = description,
        isEnabled = isEnabled,
        selected = selectedType == type && isEnabled,
        onClick = { onSelect(type) },
    )
}