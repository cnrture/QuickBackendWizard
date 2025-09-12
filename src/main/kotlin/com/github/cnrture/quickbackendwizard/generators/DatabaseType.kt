package com.github.cnrture.quickbackendwizard.generators

data class DatabaseType(
    val type: String,
    val name: String,
    val description: String,
) {
    companion object {
        val MYSQL = DatabaseType("mysql", "MySQL", "Popular open-source relational database management system.")
        val H2 = DatabaseType("h2", "H2 (In-Memory)", "Lightweight in-memory database for development and testing.")
        val MARIADB = DatabaseType("mariadb", "MariaDB", "Community-developed fork of MySQL with enhanced features.")
        val POSTGRESQL = DatabaseType(
            "postgresql",
            "PostgreSQL",
            "Advanced open-source relational database with strong standards compliance."
        )
        val ALL = listOf(MYSQL, H2, MARIADB, POSTGRESQL)
    }
}
