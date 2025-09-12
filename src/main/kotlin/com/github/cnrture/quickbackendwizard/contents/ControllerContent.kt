package com.github.cnrture.quickbackendwizard.contents

fun getControllerContent(
    packageName: String,
    entityName: String,
    controllerName: String,
    serviceName: String,
    endpoint: String,
    isSwaggerEnabled: Boolean,
): String {
    val annotations = buildString {
        append("@RestController\n")
        append("@RequestMapping(\"/$endpoint\")\n")
        if (isSwaggerEnabled) {
            append("@Tag(name = \"$entityName Controller\", description = \"CRUD operations for $entityName\")\n")
        }
    }
    val imports = buildString {
        append("import $packageName.entity.$entityName\n")
        append("import $packageName.service.$serviceName\n")
        if (isSwaggerEnabled) {
            append("import io.swagger.v3.oas.annotations.Parameter\n")
            append("import io.swagger.v3.oas.annotations.tags.Tag\n")
        }
        append("import org.springframework.http.HttpStatus\n")
        append("import org.springframework.http.ResponseEntity\n")
        append("import org.springframework.web.bind.annotation.*\n")
    }
    return """
package $packageName.controller

$imports

$annotations
class $controllerName(private val service: $serviceName) {

    @Operation(summary = "Get all $entityName", description = "Retrieve a list of all $entityName")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping
    fun getAll(): ResponseEntity<List<$entityName>> {
        val entities = service.getAll()
        return if (entities.success) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(entities)
        }
    }

    @Operation(summary = "Get $entityName by ID", description = "Retrieve a single $entityName by its ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            ApiResponse(responseCode = "404", description = "$entityName not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<$entityName> {
        val entity = service.findById(id)
        return if (entity.success) {
            ResponseEntity.ok(entity)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @Operation(summary = "Create new $entityName", description = "Create a new $entityName")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Successfully created"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PostMapping
    fun create(@RequestBody entity: $entityName): ResponseEntity<$entityName> {
        val createdEntity = service.save(entity)
        return if (createdEntity.success) {
            ResponseEntity.status(HttpStatus.CREATED).body(createdEntity)
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @Operation(summary = "Update existing $entityName", description = "Update an existing $entityName by its ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully updated"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "404", description = "$entityName not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: $entityName): ResponseEntity<$entityName> {
        val updatedEntity = service.update(id, entity)
        return if (updatedEntity.success) {
            ResponseEntity.ok(updatedEntity)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @Operation(summary = "Delete $entityName", description = "Delete an existing $entityName by its ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Successfully deleted"),
            ApiResponse(responseCode = "404", description = "$entityName not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        val deleted = service.deleteById(id)
        return if (deleted.success) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}
""".trimIndent()
}