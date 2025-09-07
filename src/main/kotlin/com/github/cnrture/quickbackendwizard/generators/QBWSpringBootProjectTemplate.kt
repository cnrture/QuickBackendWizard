package com.github.cnrture.quickbackendwizard.generators

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.util.IconLoader
import com.intellij.platform.ProjectTemplate
import javax.swing.Icon

class QBWSpringBootProjectTemplate : ProjectTemplate {
    override fun getName(): String = "Quick Backend Wizard - Kotlin Spring Boot"
    override fun getDescription(): String = "Spring boot project with Quick Backend Wizard configuration"
    override fun getIcon(): Icon? = IconLoader.getIcon("/icons/pluginIcon.svg", javaClass)
    override fun createModuleBuilder(): ModuleBuilder = QBWSpringBootModuleBuilder()
    override fun validateSettings(): ValidationInfo? = null
}