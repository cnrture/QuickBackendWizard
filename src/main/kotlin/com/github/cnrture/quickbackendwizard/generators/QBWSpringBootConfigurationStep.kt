package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.theme.QBWTheme
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class QBWSpringBootConfigurationStep(private val moduleBuilder: QBWSpringBootModuleBuilder) : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        val panel = JPanel(BorderLayout())
        ComposePanel().apply {
            setContent {
                QBWTheme {
                    val pages = listOf("Project Info", "Dependencies", "Database", "Endpoints")
                    var selectedDependencies by remember { mutableStateOf(moduleBuilder.selectedDependencies.toSet()) }
                    val state = rememberPagerState(initialPage = 0) { 4 }
                    val scope = rememberCoroutineScope()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(QBWTheme.colors.black),
                    ) {
                        QBWText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            text = "Quick Backend Wizard",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(QBWTheme.colors.red, QBWTheme.colors.purple),
                                    tileMode = TileMode.Mirror,
                                ),
                            ),
                        )

                        TabRow(
                            modifier = Modifier.fillMaxWidth(),
                            selectedTabIndex = state.currentPage,
                            backgroundColor = QBWTheme.colors.black,
                            contentColor = QBWTheme.colors.red,
                        ) {
                            pages.forEachIndexed { index, page ->
                                val isSelected = index == state.currentPage
                                Tab(
                                    selected = state.currentPage == index,
                                    onClick = { scope.launch { state.animateScrollToPage(index) } },
                                    text = {
                                        QBWText(
                                            text = page,
                                            color = if (isSelected) QBWTheme.colors.red else QBWTheme.colors.lightGray,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            ),
                                        )
                                    },
                                )
                            }
                        }
                        HorizontalPager(
                            modifier = Modifier.weight(1f),
                            state = state,
                            userScrollEnabled = false,
                        ) {
                            when (it) {
                                0 -> ProjectInfoContent(moduleBuilder)
                                1 -> DependenciesContent(
                                    moduleBuilder = moduleBuilder,
                                    selectedDependencies = selectedDependencies.toList(),
                                    onDependencyChange = { dependency, isSelected ->
                                        selectedDependencies = if (isSelected) {
                                            selectedDependencies + dependency
                                        } else {
                                            selectedDependencies - dependency
                                        }
                                        moduleBuilder.selectedDependencies = selectedDependencies.toList()
                                    },
                                )

                                2 -> DatabasesContent(moduleBuilder)
                                else -> EndpointsContent(moduleBuilder)
                            }
                        }
                    }
                }
            }
            panel.add(this)
        }
        return panel
    }

    override fun updateDataModel() = Unit
}