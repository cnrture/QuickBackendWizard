package com.github.cnrture.quickbackendwizard.generators

import com.github.cnrture.quickbackendwizard.common.Utils
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
import java.io.IOException

class QBWSpringBootModuleBuilder : JavaModuleBuilder() {

    var projectName: String = "demo"
    var packageName: String = "demo"
    var groupId: String = "com.example"
    var version: String = "0.0.1-SNAPSHOT"

    var isAddGradleTasks: Boolean = false
    var selectedDatabase: String = DatabaseType.MYSQL.type
    var selectedDependencies: List<DependencyType> = emptyList()
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
        packageName = groupId.plus(".").plus(projectName)
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
                ImportSpecBuilder(project, gradleSystemId).use(ProgressExecutionMode.START_IN_FOREGROUND_ASYNC)
            )
            Utils.showInfo(
                "Project Created",
                "Kotlin Spring Boot project '$projectName' has been created successfully."
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
        createReadmeFile(root)
        createDatabaseFiles(root)
        createEnvFile(root, dbName, dbUsername, dbPassword)
        createApiResponseFile(root)
        if (selectedDependencies.contains(DependencyType.SPRING_WEB)) createWebConfigFile(root)
        if (selectedDependencies.contains(DependencyType.SWAGGER)) createSwaggerConfigFile(root)
        endpoints.forEach { endpoint ->
            createEntityFile(root, endpoint)
            createRepositoryFile(root, endpoint)
            createServiceFile(root, endpoint)
            createControllerFile(root, endpoint)
        }
    }

    private fun createBuildGradle(root: VirtualFile) {
        val dependencies = StringBuilder().apply {
            appendLine("implementation(\"org.springframework.boot:spring-boot-starter\")")
            if (selectedDependencies.contains(DependencyType.SPRING_WEB)) {
                appendLine("    implementation(\"org.springframework.boot:spring-boot-starter-web\")")
            }
            if (selectedDependencies.contains(DependencyType.SPRING_DATA_JPA)) {
                appendLine("    implementation(\"org.springframework.boot:spring-boot-starter-data-jpa\")")
            }
            if (selectedDependencies.contains(DependencyType.SPRING_SECURITY)) {
                appendLine("    implementation(\"org.springframework.boot:spring-boot-starter-security\")")
            }
            if (selectedDependencies.contains(DependencyType.VALIDATION)) {
                appendLine("    implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
            }
            appendLine("    implementation(\"org.jetbrains.kotlin:kotlin-reflect\")")
            appendLine("    implementation(\"me.paulschwarz:spring-dotenv:4.0.0\")")
            appendLine()
            appendLine("    // Database drivers")
            when (selectedDatabase) {
                "h2" -> appendLine("    implementation(\"com.h2database:h2\")")
                "mysql" -> appendLine("    implementation(\"mysql:mysql-connector-java:8.0.33\")")
                "mariadb" -> appendLine("    implementation(\"org.mariadb.jdbc:mariadb-java-client:3.1.4\")")
                "postgresql" -> appendLine("    implementation(\"org.postgresql:postgresql:42.6.0\")")
            }
            if (selectedDependencies.contains(DependencyType.SWAGGER)) {
                appendLine()
                appendLine("    // Swagger/OpenAPI Documentation")
                appendLine("    implementation(\"org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0\")")
            }
            appendLine()
            appendLine("    // Testing")
            appendLine("    testImplementation(\"org.springframework.boot:spring-boot-starter-test\")")
            appendLine("    testImplementation(\"org.jetbrains.kotlin:kotlin-test-junit5\")")
            appendLine("    testImplementation(\"io.mockk:mockk:1.13.8\")")
            appendLine("    testImplementation(\"io.mockk:mockk-jvm:1.13.8\")")
            appendLine("    testRuntimeOnly(\"org.junit.platform:junit-platform-launcher\")")
        }.toString()

        val content = getGradleContent(groupId, version, dependencies, isAddGradleTasks)
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

        val content = getControllerContent(
            packageName = packageName,
            entityName = entityName,
            controllerName = controllerName,
            serviceName = serviceName,
            endpoint = endpoint,
            isSwaggerEnabled = selectedDependencies.contains(DependencyType.SWAGGER),
        )

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

    private fun createReadmeFile(root: VirtualFile) {
        val content = getReadmeContent(projectName, selectedDatabase, endpoints)
        createFile(root, "README.md", content)
    }

    private fun createSwaggerConfigFile(root: VirtualFile) {
        if (selectedDependencies.contains(DependencyType.SWAGGER)) {
            val packageParts = packageName.split(".")
            var srcKotlinDir = root.findChild("src")
                ?.findChild("main")
                ?.findChild("kotlin")

            packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

            val configDir = srcKotlinDir?.findChild("config") ?: srcKotlinDir?.createChildDirectory(this, "config")
            val content = getSwaggerContent(projectName.uppercase(), packageName, version)

            configDir?.let { createFile(it, "SwaggerConfig.kt", content) }
        }
    }

    private fun createApiResponseFile(root: VirtualFile) {
        val packageParts = packageName.split(".")
        var srcKotlinDir = root.findChild("src")
            ?.findChild("main")
            ?.findChild("kotlin")

        packageParts.forEach { srcKotlinDir = srcKotlinDir?.findChild(it) }

        val commonDir = srcKotlinDir?.findChild("common") ?: srcKotlinDir?.createChildDirectory(this, "common")
        val content = getApiResponseContent(packageName, selectedDependencies.contains(DependencyType.SWAGGER))

        commonDir?.let { createFile(it, "ApiResponse.kt", content) }
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider,
    ): Array<ModuleWizardStep> {
        return arrayOf(QBWSpringBootConfigurationStep(this))
    }
}
