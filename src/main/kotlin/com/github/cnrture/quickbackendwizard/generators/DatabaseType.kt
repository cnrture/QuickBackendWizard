package com.github.cnrture.quickbackendwizard.generators

data class DatabaseType(
    val type: String,
    val name: String,
    val description: String,
) {
    companion object {
        val ALL = listOf(
            DatabaseType("none", "No Database", "No database will be used in the project."),
            DatabaseType("h2", "H2 (In-Memory)", "Lightweight in-memory database for development and testing."),
            DatabaseType("mysql", "MySQL", "Popular open-source relational database management system."),
            DatabaseType("mariadb", "MariaDB", "Community-developed fork of MySQL with enhanced features."),
            DatabaseType(
                "postgresql",
                "PostgreSQL",
                "Advanced open-source relational database with strong standards compliance."
            ),
        )
    }
}
