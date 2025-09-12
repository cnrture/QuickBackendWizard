package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWCheckbox
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun ProjectInfoContent(
    projectName: String,
    groupId: String,
    packageName: String,
    version: String,
    isAddGradleTasks: Boolean,
    onProjectNameChange: (String) -> Unit,
    onGroupIdChange: (String) -> Unit,
    onPackageNameChange: (String) -> Unit,
    onVersionChange: (String) -> Unit,
    onAddGradleTasksChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                onValueChange = { onProjectNameChange(it) },
            )
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Version",
                value = version,
                onValueChange = { onVersionChange(it) }
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
                onValueChange = { onGroupIdChange(it) },
            )
            InputArea(
                modifier = Modifier.weight(1f),
                placeholder = "Package Name",
                value = packageName,
                prefix = groupId.ifEmpty { "com.example" },
                onValueChange = { onPackageNameChange(it) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        QBWCheckbox(
            checked = isAddGradleTasks,
            label = "Add Supporting Gradle Tasks",
            description = "Include additional Gradle tasks for easier project management.",
            onCheckedChange = { onAddGradleTasksChange(it) }
        )
    }
}

@Composable
private fun InputArea(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    prefix: String? = null,
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
            prefix = prefix,
            onValueChange = onValueChange,
        )
    }
}