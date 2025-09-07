package com.github.cnrture.quickbackendwizard.contents

fun getGradleContent(
    groupId: String,
    version: String,
    dependenciesBlock: String,
): String = """
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
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
""".trimIndent()