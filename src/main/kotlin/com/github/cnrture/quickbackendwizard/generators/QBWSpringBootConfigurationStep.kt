package com.github.cnrture.quickbackendwizard.generators

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class QBWSpringBootConfigurationStep(private val moduleBuilder: QBWSpringBootModuleBuilder) : ModuleWizardStep() {

    private val projectNameField = JBTextField("demo")
    private val packageNameField = JBTextField("com.example.demo")
    private val groupIdField = JBTextField("com.example")
    private val versionField = JBTextField("0.0.1-SNAPSHOT")

    private val springWebCheckbox = JBCheckBox("Spring Web", false)
    private val springDataJpaCheckbox = JBCheckBox("Spring Data JPA", false)
    private val h2DatabaseCheckbox = JBCheckBox("H2 Database", false)
    private val springSecurityCheckbox = JBCheckBox("Spring Security", false)
    private val springValidationCheckbox = JBCheckBox("Validation", false)

    private val panel = createPanel()

    private fun createPanel(): JPanel {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Project Name:"), projectNameField, 1, false)
            .addLabeledComponent(JBLabel("Group:"), groupIdField, 1, false)
            .addLabeledComponent(JBLabel("Package Name:"), packageNameField, 1, false)
            .addLabeledComponent(JBLabel("Version:"), versionField, 1, false)
            .addSeparator()
            .addComponent(JBLabel("Dependencies:"))
            .addComponent(springWebCheckbox)
            .addComponent(springDataJpaCheckbox)
            .addComponent(h2DatabaseCheckbox)
            .addComponent(springSecurityCheckbox)
            .addComponent(springValidationCheckbox)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun getComponent(): JComponent = panel

    override fun updateDataModel() {
        moduleBuilder.projectName = projectNameField.text
        moduleBuilder.packageName = packageNameField.text
        moduleBuilder.groupId = groupIdField.text
        moduleBuilder.version = versionField.text

        moduleBuilder.includeSpringWeb = springWebCheckbox.isSelected
        moduleBuilder.includeSpringDataJpa = springDataJpaCheckbox.isSelected
        moduleBuilder.includeH2Database = h2DatabaseCheckbox.isSelected
        moduleBuilder.includeSprinSecurity = springSecurityCheckbox.isSelected
        moduleBuilder.includeValidation = springValidationCheckbox.isSelected
    }

    override fun validate(): Boolean {
        if (projectNameField.text.isBlank()) return false
        if (packageNameField.text.isBlank()) return false
        if (groupIdField.text.isBlank()) return false
        return true
    }
}