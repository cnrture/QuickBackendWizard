package com.github.cnrture.quickbackendwizard.contents

fun getEnvironmentContent(
    dbName: String,
    dbUsername: String,
    dbPassword: String,
) = buildString {
    appendLine("# Application Environment Configuration")
    appendLine("# This file contains environment-specific settings for the application.")
    appendLine()
    appendLine("# Database Configuration")
    appendLine("DB_PASSWORD=$dbPassword")
    appendLine("DB_HOST=localhost")
    appendLine("DB_PORT=3306")
    appendLine("DB_NAME=$dbName")
    appendLine("DB_USERNAME=$dbUsername")
    appendLine()
    appendLine("ALLOWED_ORIGINS=*")
    appendLine("ALLOWED_METHODS=GET,POST,PUT,DELETE,OPTIONS,PATCH")
    appendLine("ALLOWED_HEADERS=Authorization,Content-Type,Accept,Origin,User-Agent,DNT,Cache-Control,X-Mx-ReqToken,Keep-Alive,X-Requested-With,If-Modified-Since")
    appendLine("CORS_MAX_AGE=3600")
}