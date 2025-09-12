package com.github.cnrture.quickbackendwizard.contents

fun getReadmeContent(
    projectName: String,
    selectedDatabase: String,
    endpoints: List<String>,
) = """
# $projectName

A Spring Boot application generated with Quick Backend Wizard.

## Features

- Spring Boot 3.3.5
- Kotlin
- ${selectedDatabase.uppercase()} Database
${if (endpoints.isNotEmpty()) "- Generated REST APIs: ${endpoints.joinToString(", ")}" else ""}

## Getting Started

### Prerequisites

- Java 21 or higher
- ${getDatabasePrerequisite(selectedDatabase)}

### Environment Configuration

This project uses environment variables for configuration. You can either:

1. **Use the provided .env file** (recommended for development):
   - The project includes a `.env` file with default values
   - Modify the values in `.env` file according to your setup

2. **Set environment variables manually**:
   ```bash
   export DB_NAME=your_database_name
   export DB_USERNAME=your_username
   export DB_PASSWORD=your_password
   ```

3. **Use default values**:
   - If no environment variables are set, the application will use default values:
     - DB_NAME: testdb
     - DB_USERNAME: ${getDefaultUsername(selectedDatabase)}
     - DB_PASSWORD: ${getDefaultPassword(selectedDatabase)}

### Database Setup

${getDatabaseSetupInstructions(selectedDatabase)}

### Running the Application

1. **Using Gradle Wrapper**:
   ```bash
   ./gradlew bootRun
   ```

2. **Using IDE**:
   - Import the project as a Gradle project
   - Run the main application class

3. **Building JAR**:
   ```bash
   ./gradlew bootJar
   java -jar build/libs/$projectName-*.jar
   ```

### API Endpoints

The application will be available at: `http://localhost:8080/$projectName`

${if (endpoints.isNotEmpty()) getEndpointsDocumentation(endpoints) else "No specific endpoints were generated."}

### Development

- Application properties: `src/main/resources/application.properties`
- Environment variables: `.env` file in project root
- Database schema: `create_tables.sql` (if applicable)

### Troubleshooting

**Database Connection Issues:**
- Make sure your database server is running
- Check your environment variables or .env file
- Verify database credentials and connection URL

**Build Issues:**
- Make sure you have Java 21 or higher
- Clean and rebuild: `./gradlew clean build`

## Generated Structure

```
$projectName/
├── src/main/kotlin/
│   └── [package]/
│       ├── Application.kt
│       ├── config/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       └── entity/
├── src/main/resources/
│   └── application.properties
├── .env
├── build.gradle.kts
└── README.md
```
""".trimIndent()

private fun getDatabasePrerequisite(database: String): String = when (database) {
    "mysql" -> "MySQL 8.0 or higher"
    "mariadb" -> "MariaDB 10.6 or higher"
    "postgresql" -> "PostgreSQL 13 or higher"
    "h2" -> "No additional database required (H2 embedded)"
    else -> "Database server"
}

private fun getDefaultUsername(database: String): String = when (database) {
    "postgresql" -> "postgres"
    "h2" -> "sa"
    else -> "root"
}

private fun getDefaultPassword(database: String): String = when (database) {
    "h2" -> "(empty)"
    else -> "password"
}

private fun getDatabaseSetupInstructions(database: String): String = when (database) {
    "mysql" -> """
1. Install and start MySQL server
2. Create a database: `CREATE DATABASE testdb;`
3. Update .env file with your MySQL credentials
""".trimIndent()

    "mariadb" -> """
1. Install and start MariaDB server  
2. Create a database: `CREATE DATABASE testdb;`
3. Update .env file with your MariaDB credentials
""".trimIndent()

    "postgresql" -> """
1. Install and start PostgreSQL server
2. Create a database: `CREATE DATABASE testdb;`
3. Update .env file with your PostgreSQL credentials
""".trimIndent()

    "h2" -> """
H2 is an embedded database - no additional setup required.
You can access the H2 console at: http://localhost:8080/h2-console
""".trimIndent()

    else -> "Please refer to DATABASE_SETUP.md for detailed instructions."
}

private fun getEndpointsDocumentation(endpoints: List<String>): String {
    val endpointDocs = endpoints.joinToString("\n\n") { endpoint ->
        val entityName = endpoint.removeSuffix("s").replaceFirstChar { it.uppercase() }
        """
**$entityName Management:**
- GET `/$endpoint` - List all $endpoint
- GET `/$endpoint/{id}` - Get ${entityName.lowercase()} by ID
- POST `/$endpoint` - Create new ${entityName.lowercase()}
- PUT `/$endpoint/{id}` - Update ${entityName.lowercase()}
- DELETE `/$endpoint/{id}` - Delete ${entityName.lowercase()}
""".trimIndent()
    }

    return """
Generated API endpoints:

$endpointDocs

All endpoints return JSON responses and expect JSON request bodies where applicable.
""".trimIndent()
}