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
) = """
spring.application.name=$projectName
""".trimIndent()