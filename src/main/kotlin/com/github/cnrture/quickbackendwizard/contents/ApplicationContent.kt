package com.github.cnrture.quickbackendwizard.contents

fun getApplicationContent(
    packageName: String,
    className: String,
) = buildString {
    appendLine("package $packageName")
    appendLine()
    appendLine("import org.springframework.boot.autoconfigure.SpringBootApplication")
    appendLine("import org.springframework.boot.runApplication")
    appendLine()
    appendLine("@SpringBootApplication")
    appendLine("class $className")
    appendLine("fun main(args: Array<String>) {")
    appendLine("    runApplication<$className>(*args)")
    appendLine("}")
}

fun getApplicationPropertiesContent(
    projectName: String,
    selectedDatabase: String,
): String {
    val h2 = buildString {
        appendLine("spring.datasource.url=jdbc:h2:mem:testdb")
        appendLine("spring.datasource.driverClassName=org.h2.Driver")
        appendLine("spring.datasource.username=sa")
        appendLine("spring.datasource.password=")
        appendLine("spring.h2.console.enabled=true")
        appendLine("spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        appendLine("spring.jpa.hibernate.ddl-auto=create-drop")
        appendLine("spring.jpa.show-sql=true")
    }
    val mysql = buildString {
        appendLine("spring.datasource.url=jdbc:mysql://localhost:3306/testdb")
        appendLine("spring.datasource.username=root")
        appendLine("spring.datasource.password=password")
        appendLine("spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver")
        appendLine("spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect")
        appendLine("spring.jpa.hibernate.ddl-auto=update")
        appendLine("spring.jpa.show-sql=true")
    }
    val mariadb = buildString {
        appendLine("spring.datasource.url=jdbc:mariadb://localhost:3306/testdb")
        appendLine("spring.datasource.username=root")
        appendLine("spring.datasource.password=password")
        appendLine("spring.datasource.driver-class-name=org.mariadb.jdbc.Driver")
        appendLine("spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect")
        appendLine("spring.jpa.hibernate.ddl-auto=update")
        appendLine("spring.jpa.show-sql=true")
    }
    val postgresql = buildString {
        appendLine("spring.datasource.url=jdbc:postgresql://localhost:5432/testdb")
        appendLine("spring.datasource.username=postgres")
        appendLine("spring.datasource.password=password")
        appendLine("spring.datasource.driver-class-name=org.postgresql.Driver")
        appendLine("spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect")
        appendLine("spring.jpa.hibernate.ddl-auto=update")
        appendLine("spring.jpa.show-sql=true")
    }

    return when (selectedDatabase) {
        "h2" -> h2
        "mysql" -> mysql
        "mariadb" -> mariadb
        "postgresql" -> postgresql
        else -> buildString {
            appendLine("spring.application.name=$projectName")
            appendLine()
            appendLine("# Server Configuration")
            appendLine("server.port=8080")
            appendLine("server.servlet.context-path=/api/v1")
            appendLine()
            appendLine("# Cors Configuration (Environment-based for security)")
            appendLine("cors.allowed-origins=${'$'}{ALLOWED_ORIGINS:*}")
            appendLine("cors.allowed-methods=${'$'}{ALLOWED_METHODS:GET,POST,PUT,DELETE,OPTIONS,PATCH}")
            appendLine("cors.allowed-headers=${'$'}{ALLOWED_HEADERS:Authorization,Content-Type,Accept,Origin,User-Agent,DNT,Cache-Control,X-Mx-ReqToken,Keep-Alive,X-Requested-With,If-Modified-Since}")
            appendLine("cors.max-age=${'$'}{CORS_MAX_AGE:3600}")
        }
    }
}
