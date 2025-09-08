package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWCheckbox
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class QBWSpringBootConfigurationStep(private val moduleBuilder: QBWSpringBootModuleBuilder) : ModuleWizardStep() {

    override fun getComponent(): JComponent {
        val panel = JPanel(BorderLayout())
        ComposePanel().apply {
            setContent {
                QBWTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(QBWTheme.colors.black),
                    ) {
                        QBWText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            text = "Quick Project Wizard",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(QBWTheme.colors.red, QBWTheme.colors.purple, QBWTheme.colors.green),
                                    tileMode = TileMode.Mirror,
                                ),
                            ),
                        )
                        MainContent()
                    }
                }
            }
            panel.add(this)
        }
        return panel
    }

    @Composable
    private fun MainContent() {
        var projectName by remember { mutableStateOf(moduleBuilder.projectName) }
        var groupId by remember { mutableStateOf(moduleBuilder.groupId) }
        var packageName by remember { mutableStateOf(moduleBuilder.packageName) }
        var version by remember { mutableStateOf(moduleBuilder.version) }
        var isSpringWebSelected by remember { mutableStateOf(false) }
        var isSpringDataJpaSelected by remember { mutableStateOf(false) }
        var isH2DatabaseSelected by remember { mutableStateOf(false) }
        var isSpringSecuritySelected by remember { mutableStateOf(false) }
        var isValidationSelected by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(QBWTheme.colors.gray)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QBWText(
                text = "Spring Boot Configuration",
                color = QBWTheme.colors.white,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            QBWText(
                text = "Configure your Spring Boot project settings and dependencies.",
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
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = QBWTheme.colors.lightGray,
            )
            QBWText(
                text = "Select Dependencies:",
                color = QBWTheme.colors.white,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            QBWCheckbox(
                checked = isSpringWebSelected,
                label = "Spring Web",
                color = QBWTheme.colors.green,
                onCheckedChange = {
                    isSpringWebSelected = it
                    moduleBuilder.includeSpringWeb = it
                },
            )
            QBWCheckbox(
                checked = isSpringDataJpaSelected,
                label = "Spring Data JPA",
                color = QBWTheme.colors.green,
                onCheckedChange = {
                    isSpringDataJpaSelected = it
                    moduleBuilder.includeSpringDataJpa = it
                },
            )
            QBWCheckbox(
                checked = isH2DatabaseSelected,
                label = "H2 Database",
                color = QBWTheme.colors.green,
                onCheckedChange = {
                    isH2DatabaseSelected = it
                    moduleBuilder.includeH2Database = it
                },
            )
            QBWCheckbox(
                checked = isSpringSecuritySelected,
                label = "Spring Security",
                color = QBWTheme.colors.green,
                onCheckedChange = {
                    isSpringSecuritySelected = it
                    moduleBuilder.includeSpringSecurity = it
                },
            )
            QBWCheckbox(
                checked = isValidationSelected,
                label = "Validation",
                color = QBWTheme.colors.green,
                onCheckedChange = {
                    isValidationSelected = it
                    moduleBuilder.includeValidation = it
                },
            )
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

    override fun updateDataModel() = Unit
}