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
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun ProjectInfoContent(moduleBuilder: QBWSpringBootModuleBuilder) {
    var projectName by remember { mutableStateOf(moduleBuilder.projectName) }
    var groupId by remember { mutableStateOf(moduleBuilder.groupId) }
    var packageName by remember { mutableStateOf(moduleBuilder.packageName) }
    var version by remember { mutableStateOf(moduleBuilder.version) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QBWTheme.colors.gray)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QBWText(
            text = "Project Information",
            color = QBWTheme.colors.white,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        QBWText(
            text = "Provide basic information about your project.",
            color = QBWTheme.colors.lightGray,
            style = TextStyle(fontSize = 14.sp),
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = QBWTheme.colors.lightGray,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Project Name",
                value = projectName,
                onValueChange = {
                    projectName = it
                    moduleBuilder.projectName = it
                },
            )
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Version",
                value = version,
                onValueChange = {
                    version = it
                    moduleBuilder.version = it
                },
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Group",
                value = groupId,
                onValueChange = {
                    groupId = it
                    moduleBuilder.groupId = it
                },
            )
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Package Name",
                value = packageName,
                onValueChange = {
                    packageName = it
                    moduleBuilder.packageName = it
                },
            )
        }
    }
}

@Composable
private fun InputArea(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QBWText(
            text = placeholder,
            color = QBWTheme.colors.white,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        QBWTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = placeholder,
            value = value,
            onValueChange = onValueChange,
        )
    }
}