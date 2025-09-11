package com.github.cnrture.quickbackendwizard.contents

import com.github.cnrture.quickbackendwizard.generators.EndpointInfo

fun getSqlCreateTableScript(
    endpoints: List<EndpointInfo>,
    databaseType: String,
    projectName: String = "demo",
): String {
    if (endpoints.isEmpty() || databaseType == "none" || databaseType == "h2") {
        return ""
    }

    val scripts = mutableListOf<String>()
    scripts.add(getDatabaseCreationScript(databaseType, projectName))
    scripts.add(getUserCreationScript(databaseType, projectName))

    endpoints.forEach { endpoint ->
        scripts.add(getTableCreationScript(endpoint, databaseType))
    }

    return scripts.joinToString("\n\n")
}

private fun getDatabaseCreationScript(databaseType: String, projectName: String): String {
    return when (databaseType) {
        "mysql", "mariadb" -> """
-- Create database (run this first)
CREATE DATABASE IF NOT EXISTS ${projectName.lowercase()};
USE ${projectName.lowercase()};
        """.trimIndent()

        "postgresql" -> """
-- Create database (run this first)
CREATE DATABASE ${projectName.lowercase()};
\c ${projectName.lowercase()};
        """.trimIndent()

        else -> ""
    }
}

private fun getUserCreationScript(databaseType: String, projectName: String): String {
    return when (databaseType) {
        "mysql", "mariadb" -> """
-- Create user and grant permissions (run as root or admin user)
-- Option 1: Create a new user (recommended for production)
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ${projectName.lowercase()}.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

-- Option 2: If you want to use root user, ensure root has a password set
-- ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
-- FLUSH PRIVILEGES;

-- Option 3: For development only - allow root without password (NOT RECOMMENDED)
-- ALTER USER 'root'@'localhost' IDENTIFIED BY '';
-- FLUSH PRIVILEGES;
        """.trimIndent()

        "postgresql" -> """
-- Create user and grant permissions (run as postgres superuser)
-- Option 1: Create a new user (recommended for production)
CREATE USER appuser WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE ${projectName.lowercase()} TO appuser;

-- Option 2: If using postgres user, ensure password is set
-- ALTER USER postgres PASSWORD 'password';
        """.trimIndent()

        else -> ""
    }
}

private fun getTableCreationScript(endpoint: EndpointInfo, databaseType: String): String {
    val tableName = endpoint.name
    val entityName = endpoint.name.removeSuffix("s").replaceFirstChar { it.uppercase() }

    return when (databaseType) {
        "mysql", "mariadb" -> """
-- Create table for $entityName
CREATE TABLE IF NOT EXISTS $tableName (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
        """.trimIndent()

        "postgresql" -> """
-- Create table for $entityName
CREATE TABLE IF NOT EXISTS $tableName (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create trigger for updated_at in PostgreSQL
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_${tableName}_updated_at 
    BEFORE UPDATE ON $tableName 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();
        """.trimIndent()

        else -> ""
    }
}

fun getDatabaseSetupInstructions(databaseType: String, projectName: String): String {
    return when (databaseType) {
        "mysql" -> """
# MySQL Database Setup Instructions

## 1. Install MySQL
- Download and install MySQL from https://dev.mysql.com/downloads/
- Or use Docker: `docker run --name mysql-db -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql:8.0`

## 2. Connect to MySQL as root
```bash
mysql -u root -p
# Enter your root password when prompted
```

## 3. Create Database and User
```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS ${projectName.lowercase()};

-- Create a user for your application (recommended)
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ${projectName.lowercase()}.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

-- Use the database
USE ${projectName.lowercase()};
```

## 4. Run the Table Creation Scripts
- Copy the table creation SQL from the generated `create_tables.sql` file
- Run them in your MySQL client or use: `mysql -u appuser -p ${projectName.lowercase()} < create_tables.sql`

## 5. Update application.properties
Update your application.properties with the correct credentials:
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

## Troubleshooting Authentication Issues
If you get "Access denied" errors:

### Option A: Use the created app user (recommended)
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

### Option B: Fix root user password
```sql
-- Connect as root and set password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'your_password';
FLUSH PRIVILEGES;
```

### Option C: Docker MySQL setup
```bash
# For Docker MySQL, use:
docker exec -it mysql-db mysql -u root -p
# Default password is what you set in MYSQL_ROOT_PASSWORD
```
        """.trimIndent()

        "mariadb" -> """
# MariaDB Database Setup Instructions

## 1. Install MariaDB
- Download and install MariaDB from https://mariadb.org/download/
- Or use Docker: `docker run --name mariadb-db -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mariadb:latest`

## 2. Connect to MariaDB as root
```bash
mysql -u root -p
# Enter your root password when prompted
```

## 3. Create Database and User
```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS ${projectName.lowercase()};

-- Create a user for your application (recommended)
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ${projectName.lowercase()}.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

-- Use the database
USE ${projectName.lowercase()};
```

## 4. Run the Table Creation Scripts
- Copy the table creation SQL from the generated `create_tables.sql` file
- Run them in your MariaDB client or use: `mysql -u appuser -p ${projectName.lowercase()} < create_tables.sql`

## 5. Update application.properties
Update your application.properties with the correct credentials:
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

## Troubleshooting Authentication Issues
If you get "Access denied" errors:

### Option A: Use the created app user (recommended)
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

### Option B: Fix root user password
```sql
-- Connect as root and set password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'your_password';
FLUSH PRIVILEGES;
```

### Option C: Docker MariaDB setup
```bash
# For Docker MariaDB, use:
docker exec -it mariadb-db mysql -u root -p
# Default password is what you set in MYSQL_ROOT_PASSWORD
```
        """.trimIndent()

        "postgresql" -> """
# PostgreSQL Database Setup Instructions

## 1. Install PostgreSQL
- Download and install PostgreSQL from https://www.postgresql.org/download/
- Or use Docker: `docker run --name postgres-db -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:15`

## 2. Connect to PostgreSQL as superuser
```bash
psql -U postgres
# Enter your postgres password when prompted
```

## 3. Create Database and User
```sql
-- Create the database
CREATE DATABASE ${projectName.lowercase()};

-- Create a user for your application (recommended)
CREATE USER appuser WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE ${projectName.lowercase()} TO appuser;

-- Connect to the new database
\c ${projectName.lowercase()};

-- Grant schema permissions to the user
GRANT ALL ON SCHEMA public TO appuser;
```

## 4. Run the Table Creation Scripts
- Copy the table creation SQL from the generated `create_tables.sql` file
- Run them in your PostgreSQL client or use: `psql -U appuser -d ${projectName.lowercase()} -f create_tables.sql`

## 5. Update application.properties
Update your application.properties with the correct credentials:
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

## Troubleshooting Authentication Issues
If you get authentication errors:

### Option A: Use the created app user (recommended)
```properties
spring.datasource.username=appuser
spring.datasource.password=password
```

### Option B: Use postgres superuser
```properties
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password
```

### Option C: Docker PostgreSQL setup
```bash
# For Docker PostgreSQL, use:
docker exec -it postgres-db psql -U postgres
# Default password is what you set in POSTGRES_PASSWORD
```

### Option D: Check pg_hba.conf for authentication method
If you're still having issues, check your PostgreSQL configuration:
```bash
# Find pg_hba.conf location
psql -U postgres -c "SHOW hba_file;"
# Edit the file to ensure proper authentication method (md5 or scram-sha-256)
```
        """.trimIndent()

        "h2" -> """
# H2 Database Setup Instructions

## No setup required!
- H2 is an in-memory database that starts automatically with your application
- Access the H2 console at: http://localhost:8080/h2-console
- Use the connection details from application.properties
- Tables will be created automatically when you start the application
        """.trimIndent()

        else -> """
# No database selected
- Select a database type in the Dependencies section to get setup instructions
        """.trimIndent()
    }
}
