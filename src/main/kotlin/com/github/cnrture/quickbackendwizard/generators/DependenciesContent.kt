package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWCheckbox
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun DependenciesContent(moduleBuilder: QBWSpringBootModuleBuilder) {
    var isSpringWebSelected by remember { mutableStateOf(false) }
    var isSpringDataJpaSelected by remember { mutableStateOf(false) }
    var isSpringSecuritySelected by remember { mutableStateOf(false) }
    var isValidationSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QBWTheme.colors.gray)
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
        QBWCheckbox(
            checked = isSpringWebSelected,
            label = "Spring Web",
            onCheckedChange = {
                isSpringWebSelected = it
                moduleBuilder.includeSpringWeb = it
            },
        )
        QBWCheckbox(
            checked = isSpringDataJpaSelected,
            label = "Spring Data JPA",
            onCheckedChange = {
                isSpringDataJpaSelected = it
                moduleBuilder.includeSpringDataJpa = it
            },
        )
        QBWCheckbox(
            checked = isSpringSecuritySelected,
            label = "Spring Security",
            onCheckedChange = {
                isSpringSecuritySelected = it
                moduleBuilder.includeSpringSecurity = it
            },
        )
        QBWCheckbox(
            checked = isValidationSelected,
            label = "Validation",
            onCheckedChange = {
                isValidationSelected = it
                moduleBuilder.includeValidation = it
            },
        )
    }
}
