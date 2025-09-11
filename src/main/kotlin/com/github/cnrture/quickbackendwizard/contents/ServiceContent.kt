package com.github.cnrture.quickbackendwizard.contents

fun getServiceContent(
    packageName: String,
    entityName: String,
    serviceName: String,
    repositoryName: String,
) = """
package $packageName.service

import $packageName.entity.$entityName
import $packageName.repository.$repositoryName
import org.springframework.stereotype.Service

@Service
class $serviceName(private val repository: $repositoryName) {

    fun findAll(): List<$entityName> = repository.findAll()

    fun findById(id: Long): $entityName = repository.findById(id).orElseThrow { 
        NoSuchElementException("$entityName not found with id: ${'$'}id") 
    }

    fun save(entity: $entityName): $entityName = repository.save(entity)

    fun update(id: Long, entity: $entityName): $entityName {
        return if (repository.existsById(id)) {
            repository.save(entity.copy(id = id))
        } else {
            throw NoSuchElementException("$entityName not found with id: ${'$'}id")
        }
    }

    fun deleteById(id: Long) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        } else {
            throw NoSuchElementException("$entityName not found with id: ${'$'}id")
        }
    }
}
""".trimIndent()