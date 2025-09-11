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
    selectedDatabase: String = "none",
) = when (selectedDatabase) {
    "h2" -> """
spring.application.name=$projectName

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
""".trimIndent()

    "mysql" -> """
spring.application.name=$projectName

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/${projectName.lowercase()}
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
""".trimIndent()

    "mariadb" -> """
spring.application.name=$projectName

# MariaDB Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/${projectName.lowercase()}
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
""".trimIndent()

    "postgresql" -> """
spring.application.name=$projectName

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/${projectName.lowercase()}
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
""".trimIndent()

    else -> """
spring.application.name=$projectName
""".trimIndent()
}
