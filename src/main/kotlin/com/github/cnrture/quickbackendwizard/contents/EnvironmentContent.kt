package com.github.cnrture.quickbackendwizard.contents

fun getEnvironmentContent(
    dbName: String,
    dbUsername: String,
    dbPassword: String,
) = """
# Application Environment Configuration
# This file contains environment-specific settings for the application.

# Database Configuration
DB_PASSWORD=$dbPassword
DB_HOST=localhost
DB_PORT=3306
DB_NAME=$dbName
DB_USERNAME=$dbUsername

ALLOWED_ORIGINS=*
ALLOWED_METHODS=GET,POST,PUT,DELETE,OPTIONS,PATCH
ALLOWED_HEADERS=Authorization,Content-Type,Accept,Origin,User-Agent,DNT,Cache-Control,X-Mx-ReqToken,Keep-Alive,X-Requested-With,If-Modified-Since
CORS_MAX_AGE=3600
""".trimIndent()