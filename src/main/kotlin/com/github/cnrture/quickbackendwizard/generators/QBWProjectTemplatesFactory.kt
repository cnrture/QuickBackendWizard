package com.github.cnrture.quickbackendwizard.generators

import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.util.IconLoader
import com.intellij.platform.ProjectTemplate
import com.intellij.platform.ProjectTemplatesFactory
import javax.swing.Icon

class QBWProjectTemplatesFactory : ProjectTemplatesFactory() {

    override fun getGroups(): Array<String> = arrayOf("Quick Backend Wizard")

    override fun getGroupIcon(group: String?): Icon? {
        return IconLoader.getIcon("/icons/pluginIcon.svg", javaClass)
    }

    override fun createTemplates(group: String?, context: WizardContext): Array<ProjectTemplate> {
        return arrayOf(QBWSpringBootProjectTemplate())
    }
}