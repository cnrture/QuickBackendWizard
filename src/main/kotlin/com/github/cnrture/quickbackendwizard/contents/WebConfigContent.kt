package com.github.cnrture.quickbackendwizard.contents

fun getWebConfigContent(
    packageName: String,
) = """
package $packageName.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig() : WebMvcConfigurer {

    @Value("\${'$'}{cors.allowed-origins}")
    private lateinit var allowedOrigins: String

    @Value("\${'$'}{cors.allowed-methods}")
    private lateinit var allowedMethods: String

    @Value("\${'$'}{cors.allowed-headers}")
    private lateinit var allowedHeaders: String

    @Value("\${'$'}{cors.max-age}")
    private var maxAge: Long = 3600

    override fun addCorsMappings(registry: CorsRegistry) {
        val origins = allowedOrigins.split(",").map { it.trim() }.toTypedArray()
        val methods = allowedMethods.split(",").map { it.trim() }.toTypedArray()
        val headers =
            if (allowedHeaders == "*") arrayOf("*") else allowedHeaders.split(",").map { it.trim() }.toTypedArray()

        registry.addMapping("/**")
            .allowedOrigins(*origins)
            .allowedMethods(*methods)
            .allowedHeaders(*headers)
            .maxAge(maxAge)
    }
}
""".trimIndent()