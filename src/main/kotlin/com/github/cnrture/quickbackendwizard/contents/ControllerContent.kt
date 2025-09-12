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

    val getAll = buildString {
        if (isSwaggerEnabled) {
            append("@Operation(summary = \"Get all $entityName\", description = \"Retrieve a list of all $entityName\")\n")
            append("@ApiResponses(\n")
            append("    value = [\n")
            append("        ApiResponse(\n")
            append("            success = true, message = \"Successfully retrieved\",\n")
            append("            content = [Content(schema = Schema(implementation = ApiResponse::class))],\n")
            append("        ),\n")
            append("        ApiResponse(success = false, message = \"No content\"),\n")
            append("    ]\n")
            append(")\n")
        }
        append("@GetMapping\n")
        append("fun getAll(): ResponseEntity<ApiResponse<List<$entityName>>> {\n")
        append("    val entities = service.getAll()\n")
        append("    return if (entities.success) {\n")
        append("        ResponseEntity.noContent().build()\n")
        append("    } else {\n")
        append("        ResponseEntity.ok(entities)\n")
        append("    }\n")
        append("}\n")
    }

    val getById = buildString {
        if (isSwaggerEnabled) {
            append("@Operation(summary = \"Get $entityName by ID\", description = \"Retrieve a single $entityName by its ID\")\n")
            append("@ApiResponses(\n")
            append("    value = [\n")
            append("        ApiResponse(\n")
            append("            success = true, message = \"Successfully retrieved\",\n")
            append("            content = [Content(schema = Schema(implementation = ApiResponse::class))],\n")
            append("        ),\n")
            append("        ApiResponse(success = false, message = \"$entityName not found\", data = null),\n")
            append("    ]\n")
            append(")\n")
        }
        append("@GetMapping(\"/{id}\")\n")
        append("fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<$entityName>> {\n")
        append("    val entity = service.findById(id)\n")
        append("    return if (entity.success) {\n")
        append("        ResponseEntity.ok(entity)\n")
        append("    } else {\n")
        append("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()\n")
        append("    }\n")
        append("}\n")
    }

    val create = buildString {
        if (isSwaggerEnabled) {
            append("@Operation(summary = \"Create new $entityName\", description = \"Create a new $entityName\")\n")
            append("@ApiResponses(\n")
            append("    value = [\n")
            append("        ApiResponse(\n")
            append("            success = true, message = \"Successfully created\",\n")
            append("            content = [Content(schema = Schema(implementation = ApiResponse::class))],\n")
            append("        ),\n")
            append("        ApiResponse(success = false, message = \"Invalid input\"),\n")
            append("        ApiResponse(responseCode = \"500\", description = \"Internal server error\")\n")
            append("    ]\n")
            append(")\n")
        }
        append("@PostMapping\n")
        append("fun create(@RequestBody entity: $entityName): ResponseEntity<ApiResponse<$entityName>> {\n")
        append("    val createdEntity = service.save(entity)\n")
        append("    return if (createdEntity.success) {\n")
        append("        ResponseEntity.status(HttpStatus.CREATED).body(createdEntity)\n")
        append("    } else {\n")
        append("        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()\n")
        append("    }\n")
        append("}\n")
    }

    val update = buildString {
        if (isSwaggerEnabled) {
            append("@Operation(summary = \"Update existing $entityName\", description = \"Update an existing $entityName by its ID\")\n")
            append("@ApiResponses(\n")
            append("    value = [\n")
            append("        ApiResponse(\n")
            append("            success = true, message = \"Successfully updated\",\n")
            append("            content = [Content(schema = Schema(implementation = ApiResponse::class))],\n")
            append("        ),\n")
            append("        ApiResponse(success = false, message = \"$entityName not found\"),\n")
            append("    ]\n")
            append(")\n")
        }
        append("@PutMapping(\"/{id}\")\n")
        append("fun update(@PathVariable id: Long, @RequestBody entity: $entityName): ResponseEntity<ApiResponse<$entityName>> {\n")
        append("    val updatedEntity = service.update(id, entity)\n")
        append("    return if (updatedEntity.success) {\n")
        append("        ResponseEntity.ok(updatedEntity)\n")
        append("    } else {\n")
        append("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()\n")
        append("    }\n")
        append("}\n")
    }

    val deleteById = buildString {
        if (isSwaggerEnabled) {
            append("@Operation(summary = \"Delete $entityName by ID\", description = \"Delete an existing $entityName by its ID\")\n")
            append("@ApiResponses(\n")
            append("    value = [\n")
            append("        ApiResponse(\n")
            append("            success = true, message = \"Successfully deleted\",\n")
            append("            content = [Content(schema = Schema(implementation = ApiResponse::class))],\n")
            append("        ),\n")
            append("        ApiResponse(success = false, message = \"$entityName not found\"),\n")
            append("    ]\n")
            append(")\n")
        }
        append("@DeleteMapping(\"/{id}\")\n")
        append("fun deleteById(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {\n")
        append("    val deleted = service.deleteById(id)\n")
        append("    return if (deleted.success) {\n")
        append("        ResponseEntity.noContent().build()\n")
        append("    } else {\n")
        append("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()\n")
        append("    }\n")
        append("}\n")
    }
    return """
package $packageName.controller

$imports

$annotations
class $controllerName(private val service: $serviceName) {

    $getAll

    $getById

    $create

    $update

    $deleteById
}
""".trimIndent()
}