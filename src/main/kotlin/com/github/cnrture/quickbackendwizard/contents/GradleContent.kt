package com.github.cnrture.quickbackendwizard.contents

fun getGradleContent(
    groupId: String,
    version: String,
    dependenciesBlock: String,
    isAddGradleTasks: Boolean,
): String = """
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "$groupId"
version = "$version"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    $dependenciesBlock
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

${
    if (isAddGradleTasks) {
        """
tasks.register("prepareDeployment") {
    group = "deployment"
    description = "Builds the application and prepares it for deployment"
    dependsOn("bootJar")

    doLast {
        println("Application built and ready for deployment")
        println("JAR file: ${'$'}{tasks.bootJar.get().archiveFile.get().asFile}")
    }
}
""".trimIndent()
    } else {
        ""
    }
}
""".trimIndent()
