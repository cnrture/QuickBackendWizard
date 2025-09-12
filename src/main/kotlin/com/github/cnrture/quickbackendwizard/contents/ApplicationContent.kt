package com.github.cnrture.quickbackendwizard.contents

fun getApplicationContent(
    packageName: String,
    className: String,
) = """
package $packageName

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class $className

fun main(args: Array<String>) {
    runApplication<$className>(*args)
}
""".trimIndent()

fun getApplicationPropertiesContent(
    projectName: String,
    selectedDatabase: String,
) = when (selectedDatabase) {
    "h2" -> """
spring.application.name=$projectName

# Server Configuration
server.port=8080
server.servlet.context-path=/${projectName.lowercase()}

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:${'$'}{DB_NAME}
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=${'$'}{DB_USERNAME}
spring.datasource.password=${'$'}{DB_PASSWORD}
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# CORS Configuration (Environment-based for security)
cors.allowed-origins=${'$'}{ALLOWED_ORIGINS}
cors.allowed-methods=${'$'}{ALLOWED_METHODS}
cors.allowed-headers=${'$'}{ALLOWED_HEADERS}
cors.max-age=${'$'}{CORS_MAX_AGE:3600}
""".trimIndent()

    "mysql" -> """
spring.application.name=$projectName

# Server Configuration
server.port=8080
server.servlet.context-path=/${projectName.lowercase().trim()}

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/${'$'}{DB_NAME}
spring.datasource.username=${'$'}{DB_USERNAME}
spring.datasource.password=${'$'}{DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# CORS Configuration (Environment-based for security)
cors.allowed-origins=${'$'}{ALLOWED_ORIGINS}
cors.allowed-methods=${'$'}{ALLOWED_METHODS}
cors.allowed-headers=${'$'}{ALLOWED_HEADERS}
cors.max-age=${'$'}{CORS_MAX_AGE:3600}
""".trimIndent()

    "mariadb" -> """
spring.application.name=$projectName

# Server Configuration
server.port=8080
server.servlet.context-path=/${projectName.lowercase().trim()}

# MariaDB Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/${'$'}{DB_NAME}
spring.datasource.username=${'$'}{DB_USERNAME}
spring.datasource.password=${'$'}{DB_PASSWORD}
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# CORS Configuration (Environment-based for security)
cors.allowed-origins=${'$'}{ALLOWED_ORIGINS}
cors.allowed-methods=${'$'}{ALLOWED_METHODS}
cors.allowed-headers=${'$'}{ALLOWED_HEADERS}
cors.max-age=${'$'}{CORS_MAX_AGE:3600}
""".trimIndent()

    "postgresql" -> """
spring.application.name=$projectName

# Server Configuration
server.port=8080
server.servlet.context-path=/${projectName.lowercase().trim()}

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/${'$'}{DB_NAME}
spring.datasource.username=${'$'}{DB_USERNAME}
spring.datasource.password=${'$'}{DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# CORS Configuration (Environment-based for security)
cors.allowed-origins=${'$'}{ALLOWED_ORIGINS}
cors.allowed-methods=${'$'}{ALLOWED_METHODS}
cors.allowed-headers=${'$'}{ALLOWED_HEADERS}
cors.max-age=${'$'}{CORS_MAX_AGE:3600}
""".trimIndent()

    else -> """
spring.application.name=$projectName

# Server Configuration
server.port=8080
server.servlet.context-path=/${projectName.lowercase().trim()}

# CORS Configuration (Environment-based for security)
cors.allowed-origins=${'$'}{ALLOWED_ORIGINS}
cors.allowed-methods=${'$'}{ALLOWED_METHODS}
cors.allowed-headers=${'$'}{ALLOWED_HEADERS}
cors.max-age=${'$'}{CORS_MAX_AGE:3600}
""".trimIndent()
}
