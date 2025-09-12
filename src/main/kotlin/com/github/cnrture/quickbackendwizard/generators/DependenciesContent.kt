package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun DependenciesContent(
    selectedDependencies: List<DependencyType>,
    onDependencyChange: (DependencyType, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QBWText(
            text = "Dependencies",
            color = QBWTheme.colors.white,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        QBWText(
            text = "Select the dependencies you want to include in your project.",
            color = QBWTheme.colors.lightGray,
            style = TextStyle(fontSize = 14.sp),
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = QBWTheme.colors.lightGray,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DependencyType.ALL.forEach { dependency ->
                val selected = selectedDependencies.contains(dependency)
                QBWCheckbox(
                    checked = selected,
                    label = dependency.name,
                    description = dependency.description,
                    onCheckedChange = { onDependencyChange(dependency, it) },
                )
            }
        }
    }
}
