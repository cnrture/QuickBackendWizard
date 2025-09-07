package com.github.cnrture.quickbackendwizard.generators

import com.github.cnrture.quickbackendwizard.contents.*
import com.intellij.ide.util.projectWizard.JavaModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException

class QBWSpringBootModuleBuilder : JavaModuleBuilder() {

    var projectName: String = "demo"
    var packageName: String = "com.example.demo"
    var groupId: String = "com.example"
    var version: String = "0.0.1-SNAPSHOT"

    var includeSpringWeb: Boolean = false
    var includeSpringDataJpa: Boolean = false
    var includeH2Database: Boolean = false
    var includeSprinSecurity: Boolean = false
    var includeValidation: Boolean = false

    override fun getBuilderId(): String = "qbw.spring.boot"

    override fun getPresentableName(): String = "QBW Spring Boot"

    override fun getDescription(): String = "Spring Boot project with Quick Backend Wizard configuration"

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        super.setupRootModel(modifiableRootModel)
        println("Setting up project structure for $projectName")
        createProjectStructure(modifiableRootModel)
    }

    private fun createProjectStructure(modifiableRootModel: ModifiableRootModel) {
        val contentEntry = doAddContentEntry(modifiableRootModel) ?: return
        val root = contentEntry.file ?: return

        try {
            createDirectoryStructure(root)

            createProjectFiles(root)
            println("Project structure created successfully.")
        } catch (e: IOException) {
            println("Error creating project structure: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun createDirectoryStructure(root: VirtualFile) {
        println("Creating directory structure...")
        val gradleDir = root.findChild("gradle") ?: root.createChildDirectory(this, "gradle")
        gradleDir.createChildDirectory(this, "wrapper")

        val srcDir = root.findChild("src") ?: root.createChildDirectory(this, "src")
        val mainDir = srcDir.findChild("main") ?: srcDir.createChildDirectory(this, "main")
        val testDir = srcDir.findChild("test") ?: srcDir.createChildDirectory(this, "test")

        val kotlinMainDir = mainDir.createChildDirectory(this, "kotlin")
        val resourcesMainDir = mainDir.createChildDirectory(this, "resources")

        val kotlinTestDir = testDir.createChildDirectory(this, "kotlin")

        val packagePath = packageName.replace(".", "/")
        createPackageDirectories(kotlinMainDir, packagePath)
        createPackageDirectories(kotlinTestDir, packagePath)
    }

    private fun createPackageDirectories(parent: VirtualFile, packagePath: String) {
        var current = parent
        packagePath.split("/").forEach { packageName ->
            current = current.createChildDirectory(this, packageName)
        }
    }

    private fun createProjectFiles(root: VirtualFile) {
        createBuildGradle(root)
        createSettingsGradle(root)
        createGradleProperties(root)
        createGradleWrapper(root)
        createMainApplicationFile(root)
        createGitIgnore(root)
    }

    private fun createBuildGradle(root: VirtualFile) {
        val dependencies = mutableListOf<String>()

        dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter\")")
        dependencies.add("implementation(\"org.jetbrains.kotlin:kotlin-reflect\")")

        if (includeSpringWeb) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-web\")")
        }
        if (includeSpringDataJpa) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-data-jpa\")")
        }
        if (includeH2Database) {
            dependencies.add("runtimeOnly(\"com.h2database:h2\")")
        }
        if (includeSprinSecurity) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-security\")")
        }
        if (includeValidation) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
        }

        dependencies.add("testImplementation(\"org.springframework.boot:spring-boot-starter-test\")")
        dependencies.add("testImplementation(\"org.jetbrains.kotlin:kotlin-test-junit5\")")
        dependencies.add("testRuntimeOnly(\"org.junit.platform:junit-platform-launcher\")")

        val dependenciesBlock = dependencies.joinToString("\n    ")
        val content = getGradleContent(groupId, version, dependenciesBlock)
        createFile(root, "build.gradle.kts", content)
    }

    private fun createSettingsGradle(root: VirtualFile) {
        val content = "rootProject.name = \"$projectName\""
        createFile(root, "settings.gradle.kts", content)
    }

    private fun createGradleProperties(root: VirtualFile) {
        createFile(root, "gradle.properties", "# Deployment Configuration")
    }

    private fun createGradleWrapper(root: VirtualFile) {
        val gradleDir = root.findChild("gradle") ?: root.createChildDirectory(this, "gradle")
        val wrapperDir = gradleDir.findChild("wrapper") ?: gradleDir.createChildDirectory(this, "wrapper")

        wrapperDir.let { wrapperDir ->
            val wrapperProperties = getWrapperPropertiesContent()
            createFile(wrapperDir, "gradle-wrapper.properties", wrapperProperties)
        }

        val gradlewContent = getGradleWContent()
        val gradlewBatContent = getGradleWBatContent()
        createFile(root, "gradlew.bat", gradlewBatContent)
        createFile(root, "gradlew", gradlewContent)
    }

    private fun createMainApplicationFile(root: VirtualFile) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { part ->
            srcKotlinDir = srcKotlinDir?.findChild(part)
        }

        val className = projectName.split("-").joinToString("") { part ->
            part.replaceFirstChar { it.uppercase() }
        } + "Application"

        val content = getApplicationContent(packageName, className)

        srcKotlinDir?.let { packageDir ->
            createFile(packageDir, "$className.kt", content)
        }
    }

    private fun createGitIgnore(root: VirtualFile) {
        val content = getGitIgnoreContent()
        createFile(root, ".gitignore", content)
    }

    private fun createFile(parent: VirtualFile, fileName: String, content: String): VirtualFile {
        val file = parent.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
        return file
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider,
    ): Array<ModuleWizardStep> {
        return arrayOf(QBWSpringBootConfigurationStep(this))
    }
}