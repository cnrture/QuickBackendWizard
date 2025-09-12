package com.github.cnrture.quickbackendwizard.contents

fun getSwaggerContent(
    projectName: String,
    packageName: String,
    version: String,
) = """
package $packageName.config
    
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("$projectName API").version("$version")
            )
            .addServersItem(
                Server().url("http://localhost:8080").description("Local server")
            )
            .addServersItem(
                Server().url("https://api.example.com").description("Production server")
            )
            // If you add authentication, uncomment the following lines to enable security schemes
            /*.components(
                Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("IdToken")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))*/
    }
}
""".trimIndent()