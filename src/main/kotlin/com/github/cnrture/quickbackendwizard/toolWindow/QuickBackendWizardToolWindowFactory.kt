package com.github.cnrture.quickbackendwizard.toolWindow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class QuickBackendWizardToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(
                createToolWindowComponent(project),
                "",
                false,
            )
        )
    }

    override fun shouldBeAvailable(project: Project) = true

    private fun createToolWindowComponent(project: Project): JComponent {
        val panel = JPanel(BorderLayout())
        ComposePanel().apply {
            setContent {
                QBWTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(QBWTheme.colors.gray),
                    ) {
                        QBWText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            text = "Quick Backend Wizard",
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(QBWTheme.colors.red, QBWTheme.colors.purple, QBWTheme.colors.green),
                                    tileMode = TileMode.Mirror,
                                ),
                            ),
                        )
                        //MainContent(project)
                    }
                }
            }
            panel.add(this)
        }
        return panel
    }
}
