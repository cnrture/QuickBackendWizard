package com.github.cnrture.quickbackendwizard.generators

data class DependencyType(
    val type: String,
    val name: String,
    val description: String,
) {
    companion object {
        val SPRING_WEB = DependencyType(
            type = "spring-boot-starter-web",
            name = "Spring Web",
            description = "Includes Spring MVC and embedded Tomcat server.",
        )
        val SPRING_DATA_JPA = DependencyType(
            type = "spring-boot-starter-data-jpa",
            name = "Spring Data JPA",
            description = "Provides integration with JPA for database access.",
        )
        val SPRING_SECURITY = DependencyType(
            type = "spring-boot-starter-security",
            name = "Spring Security",
            description = "Adds authentication and authorization support.",
        )
        val VALIDATION = DependencyType(
            type = "spring-boot-starter-validation",
            name = "Validation",
            description = "Enables bean validation using Hibernate Validator.",
        )
        val SWAGGER = DependencyType(
            type = "springdoc-openapi-starter-webmvc-ui",
            name = "Swagger/OpenAPI",
            description = "Generates API documentation using Springdoc OpenAPI.",
        )
        val ALL = listOf(SPRING_WEB, SPRING_DATA_JPA, SPRING_SECURITY, VALIDATION, SWAGGER)
    }
}
