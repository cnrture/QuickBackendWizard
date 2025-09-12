package com.github.cnrture.quickbackendwizard.generators

import com.github.cnrture.quickbackendwizard.contents.*
import com.intellij.ide.util.projectWizard.JavaModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.externalSystem.importing.ImportSpecBuilder
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.vfs.VirtualFile
import fleet.kernel.db
import java.io.IOException

class QBWSpringBootModuleBuilder : JavaModuleBuilder() {

    var projectName: String = "demo"
    var packageName: String = "com.example.demo"
    var groupId: String = "com.example"
    var version: String = "0.0.1-SNAPSHOT"

    var includeSpringWeb: Boolean = false
    var includeSpringDataJpa: Boolean = false
    var includeSpringSecurity: Boolean = false
    var includeValidation: Boolean = false
    var isAddGradleTasks: Boolean = false
    var selectedDatabase: String = DatabaseType.MYSQL.type
    var dbName: String = ""
    var dbUsername: String = ""
    var dbPassword: String = ""

    var endpoints: List<String> = emptyList()

    override fun getBuilderId(): String = "qbw.spring.boot"

    override fun getPresentableName(): String = "QBW Spring Boot"

    override fun getDescription(): String = "Kotlin spring boot project with Quick Backend Wizard configuration"

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        super.setupRootModel(modifiableRootModel)
        createProjectStructure(modifiableRootModel)
    }

    private fun createProjectStructure(modifiableRootModel: ModifiableRootModel) {
        val contentEntry = doAddContentEntry(modifiableRootModel) ?: return
        val root = contentEntry.file ?: return

        try {
            createDirectoryStructure(root)
            createProjectFiles(root)

            ApplicationManager.getApplication().invokeLater {
                ApplicationManager.getApplication().executeOnPooledThread {
                    try {
                        Thread.sleep(2000)
                        syncGradleProject(modifiableRootModel.project, root)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun syncGradleProject(project: Project, projectRoot: VirtualFile) {
        try {
            val gradleSystemId = ProjectSystemId("GRADLE")
            ExternalSystemUtil.refreshProject(
                projectRoot.path,
                ImportSpecBuilder(project, gradleSystemId)
                    .use(ProgressExecutionMode.IN_BACKGROUND_ASYNC)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createDirectoryStructure(root: VirtualFile) {
        val gradleDir = root.findChild("gradle") ?: root.createChildDirectory(this, "gradle")
        gradleDir.createChildDirectory(this, "wrapper")

        val srcDir = root.findChild("src") ?: root.createChildDirectory(this, "src")
        val mainDir = srcDir.findChild("main") ?: srcDir.createChildDirectory(this, "main")
        val testDir = srcDir.findChild("test") ?: srcDir.createChildDirectory(this, "test")

        val kotlinMainDir = mainDir.createChildDirectory(this, "kotlin")
        mainDir.createChildDirectory(this, "resources")

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
        createApplicationProperties(root)
        createWebConfigFile(root)

        createDatabaseFiles(root)
        createEnvFile(root, dbName, dbUsername, dbPassword)

        endpoints.forEach { endpoint ->
            createEntityFile(root, endpoint)
            createRepositoryFile(root, endpoint)
            createServiceFile(root, endpoint)
            createControllerFile(root, endpoint)
        }
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
        when (selectedDatabase) {
            "h2" -> {
                dependencies.add("implementation(\"com.h2database:h2\")")
            }

            "mysql" -> {
                dependencies.add("implementation(\"mysql:mysql-connector-java:8.0.33\")")
            }

            "mariadb" -> {
                dependencies.add("implementation(\"org.mariadb.jdbc:mariadb-java-client:3.1.4\")")
            }

            "postgresql" -> {
                dependencies.add("implementation(\"org.postgresql:postgresql:42.6.0\")")
            }
        }
        if (includeSpringSecurity) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-security\")")
        }
        if (includeValidation) {
            dependencies.add("implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
        }

        dependencies.add("testImplementation(\"org.springframework.boot:spring-boot-starter-test\")")
        dependencies.add("testImplementation(\"org.jetbrains.kotlin:kotlin-test-junit5\")")
        dependencies.add("testImplementation(\"io.mockk:mockk:1.13.8\")")
        dependencies.add("testImplementation(\"io.mockk:mockk-jvm:1.13.8\")")
        dependencies.add("testRuntimeOnly(\"org.junit.platform:junit-platform-launcher\")")

        val dependenciesBlock = dependencies.joinToString("\n    ")
        val content = getGradleContent(groupId, version, dependenciesBlock, isAddGradleTasks)
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

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val className = projectName.split("-").joinToString("") { part ->
            part.replaceFirstChar { it.uppercase() }
        } + "Application"

        val content = getApplicationContent(packageName, className)

        srcKotlinDir?.let { createFile(it, "$className.kt", content) }
    }

    private fun createGitIgnore(root: VirtualFile) {
        val content = getGitIgnoreContent()
        createFile(root, ".gitignore", content)
    }

    private fun createApplicationProperties(root: VirtualFile) {
        val resourcesDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("resources")

        val content = getApplicationPropertiesContent(projectName, selectedDatabase)

        resourcesDir?.let { createFile(it, "application.properties", content) }
    }

    private fun createDatabaseFiles(root: VirtualFile) {
        if (endpoints.isNotEmpty()) {
            val sqlScript = getSqlCreateTableScript(endpoints, selectedDatabase, projectName)
            if (sqlScript.isNotEmpty()) {
                createFile(root, "create_tables.sql", sqlScript)
            }

            val setupInstructions = getDatabaseSetupInstructions(selectedDatabase, projectName)
            createFile(root, "DATABASE_SETUP.md", setupInstructions)
        }
    }

    private fun createFile(parent: VirtualFile, fileName: String, content: String): VirtualFile {
        val file = parent.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
        return file
    }

    private fun createEntityFile(root: VirtualFile, endpoint: String) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val entityDir = srcKotlinDir?.findChild("entity") ?: srcKotlinDir?.createChildDirectory(this, "entity")
        val entityName = endpoint.removeSuffix("s").replaceFirstChar { it.uppercase() }
        val content = getEntityContent(packageName, entityName, endpoint)

        entityDir?.let { createFile(it, "$entityName.kt", content) }
    }

    private fun createRepositoryFile(root: VirtualFile, endpoint: String) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val repositoryDir =
            srcKotlinDir?.findChild("repository") ?: srcKotlinDir?.createChildDirectory(this, "repository")

        val entityName = endpoint.removeSuffix("s").replaceFirstChar { it.uppercase() }
        val repositoryName = "${entityName}Repository"

        val content = getRepositoryContent(packageName, entityName, repositoryName)

        repositoryDir?.let { createFile(it, "$repositoryName.kt", content) }
    }

    private fun createServiceFile(root: VirtualFile, endpoint: String) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val serviceDir = srcKotlinDir?.findChild("service") ?: srcKotlinDir?.createChildDirectory(this, "service")

        val entityName = endpoint.removeSuffix("s").replaceFirstChar { it.uppercase() }
        val serviceName = "${entityName}Service"
        val repositoryName = "${entityName}Repository"

        val content = getServiceContent(packageName, entityName, serviceName, repositoryName)

        serviceDir?.let { createFile(it, "$serviceName.kt", content) }
    }

    private fun createControllerFile(root: VirtualFile, endpoint: String) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val controllerDir =
            srcKotlinDir?.findChild("controller") ?: srcKotlinDir?.createChildDirectory(this, "controller")

        val entityName = endpoint.removeSuffix("s").replaceFirstChar { it.uppercase() }
        val serviceName = "${entityName}Service"
        val controllerName = "${entityName}Controller"

        val content = getControllerContent(packageName, entityName, controllerName, serviceName, endpoint)

        controllerDir?.let { createFile(it, "$controllerName.kt", content) }
    }

    private fun createEnvFile(root: VirtualFile, dbName: String, dbUsername: String, dbPassword: String) {
        val dbName = dbName.ifEmpty { "testdb" }
        val dbUsername = dbUsername.ifEmpty { "root" }
        val dbPassword = dbPassword.ifEmpty { "password" }
        val content = getEnvironmentContent(dbName, dbUsername, dbPassword)
        createFile(root, ".env", content)
    }

    private fun createWebConfigFile(root: VirtualFile) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val configDir = srcKotlinDir?.findChild("config") ?: srcKotlinDir?.createChildDirectory(this, "config")
        val content = getWebConfigContent(packageName)

        configDir?.let { createFile(it, "WebConfig.kt", content) }
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider,
    ): Array<ModuleWizardStep> {
        return arrayOf(QBWSpringBootConfigurationStep(this))
    }
}
