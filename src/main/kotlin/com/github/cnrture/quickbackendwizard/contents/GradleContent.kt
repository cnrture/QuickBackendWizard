package com.github.cnrture.quickbackendwizard.contents

fun getGradleContent(
    groupId: String,
    version: String,
    dependenciesBlock: String,
    isAddGradleTasks: Boolean,
): String = buildString {
    appendLine("plugins {")
    appendLine("    kotlin(\"jvm\") version \"1.9.25\"")
    appendLine("    kotlin(\"plugin.spring\") version \"1.9.25\"")
    appendLine("    id(\"org.springframework.boot\") version \"3.3.5\"")
    appendLine("    id(\"io.spring.dependency-management\") version \"1.1.7\"")
    appendLine("}")
    appendLine()
    appendLine("group = \"$groupId\"")
    appendLine("version = \"$version\"")
    appendLine()
    appendLine("java {")
    appendLine("    toolchain {")
    appendLine("        languageVersion = JavaLanguageVersion.of(21)")
    appendLine("    }")
    appendLine("}")
    appendLine()
    appendLine("repositories {")
    appendLine("    mavenCentral()")
    appendLine("}")
    appendLine()
    appendLine("dependencies {")
    appendLine("    $dependenciesBlock")
    appendLine("}")
    appendLine()
    appendLine("kotlin {")
    appendLine("    compilerOptions {")
    appendLine("        freeCompilerArgs.addAll(\"-Xjsr305=strict\")")
    appendLine("    }")
    appendLine("}")
    appendLine()
    appendLine("tasks.withType<Test> {")
    appendLine("    useJUnitPlatform()")
    appendLine("}")
    appendLine()
    if (isAddGradleTasks) {
        appendLine("tasks.register(\"prepareDeployment\") {")
        appendLine("    group = \"deployment\"")
        appendLine("    description = \"Builds the application and prepares it for deployment\"")
        appendLine("    dependsOn(\"bootJar\")")
        appendLine()
        appendLine("    doLast {")
        appendLine("        println(\"Application built and ready for deployment\")")
        appendLine("        println(\"JAR file: \${tasks.bootJar.get().archiveFile.get().asFile}\")")
        appendLine("    }")
        appendLine("}")
    }
}