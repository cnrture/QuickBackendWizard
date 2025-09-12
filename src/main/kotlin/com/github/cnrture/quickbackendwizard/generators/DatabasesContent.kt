package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
    var selectedDatabase by remember { mutableStateOf(DatabaseType.MYSQL.type) }
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
                    QBWRadioButton(
                        modifier = Modifier.weight(1f),
                        text = it.name,
                        description = it.description,
                        selected = it.type == selectedDatabase,
                        onClick = {
                            selectedDatabase = it.type
                            moduleBuilder.selectedDatabase = it.type
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
                        onValueChange = {
                            dbPassword = it
                            moduleBuilder.dbPassword = it
                        },
                    )
                }
            }
        }
    }
}