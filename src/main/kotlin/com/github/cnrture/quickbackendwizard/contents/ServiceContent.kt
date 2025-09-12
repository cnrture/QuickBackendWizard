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

    fun getAll(): ApiResponse<List<$entityName>> {
        try {
            val entities = repository.findAll()
            return ApiResponse(
                success = true,
                message = "Successfully fetched $entityName list",
                data = entities,
            )
        } catch (e: Exception) {
            return ApiResponse(
                success = false,
                message = "Error fetching $entityName list: ${'$'}{e.message}",
                data = emptyList(),
            )
        }
    }

    fun getById(id: Long): ApiResponse<$entityName> {
        try {
            val entity = repository.findById(id).orElseThrow { 
                NoSuchElementException("$entityName not found with id: ${'$'}id") 
            }
            return ApiResponse(
                success = true,
                message = "Successfully fetched $entityName",
                data = entity,
            )
        } catch (e: NoSuchElementException) {
            return ApiResponse(
                success = false,
                message = e.message ?: "No such element",
                data = null,
            )
        } catch (e: Exception) {
            return ApiResponse(
                success = false,
                message = "Error fetching $entityName: ${'$'}{e.message}",
                data = null,
            )
        }
    }

    fun create(entity: $entityName): ApiResponse<$entityName> {
        return try {
            val savedEntity = repository.save(entity)
            ApiResponse(
                success = true,
                message = "$entityName created successfully",
                data = savedEntity,
            )
        } catch (e: Exception) {
            ApiResponse(
                success = false,
                message = "Error creating $entityName: ${'$'}{e.message}",
                data = null,
            )
        }
    }

    fun update(id: Long, entity: $entityName): ApiResponse<$entityName> {
        return try {
            if (!repository.existsById(id)) {
                return ApiResponse(
                    success = false,
                    message = "$entityName not found with id: ${'$'}id",
                    data = null,
                )
            }
            val updatedEntity = repository.save(entity.copy(id = id))
            ApiResponse(
                success = true,
                message = "$entityName updated successfully",
                data = updatedEntity,
            )
        } catch (e: Exception) {
            ApiResponse(
                success = false,
                message = "Error updating $entityName: ${'$'}{e.message}",
                data = null,
            )
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