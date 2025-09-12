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
        appendLine("@RestController")
        appendLine("@RequestMapping(\"/$endpoint\")")
        if (isSwaggerEnabled) {
            appendLine("@Tag(name = \"$entityName Controller\", description = \"CRUD operations for $entityName\")")
        }
    }

    val imports = buildString {
        appendLine("import $packageName.common.ApiResponse")
        appendLine("import $packageName.entity.$entityName")
        appendLine("import $packageName.service.$serviceName")
        if (isSwaggerEnabled) {
            appendLine("import io.swagger.v3.oas.annotations.Parameter")
            appendLine("import io.swagger.v3.oas.annotations.tags.Tag")
        }
        appendLine("import org.springframework.http.HttpStatus")
        appendLine("import org.springframework.http.ResponseEntity")
        appendLine("import org.springframework.web.bind.annotation.*")
    }

    val getAll = buildString {
        if (isSwaggerEnabled) {
            appendLine("@Operation(summary = \"Get all $entityName\", description = \"Retrieve a list of all $entityName\")")
            appendLine("@ApiResponses(")
            appendLine("    value = [")
            appendLine("        ApiResponse(")
            appendLine("            success = true, message = \"Successfully retrieved\",")
            appendLine("            content = [Content(schema = Schema(implementation = ApiResponse::class))],")
            appendLine("        ),")
            appendLine("        ApiResponse(success = false, message = \"No content\"),")
            appendLine("    ]")
            appendLine(")")
        }
        appendLine("@GetMapping")
        appendLine("fun getAll(): ResponseEntity<ApiResponse<List<$entityName>>> {")
        appendLine("    val entities = service.getAll()")
        appendLine("    return if (entities.success) {")
        appendLine("        ResponseEntity.noContent().build()")
        appendLine("    } else {\n")
        appendLine("        ResponseEntity.ok(entities)")
        appendLine("    }")
        appendLine("}")
    }

    val getById = buildString {
        if (isSwaggerEnabled) {
            appendLine("@Operation(summary = \"Get $entityName by ID\", description = \"Retrieve a single $entityName by its ID\")")
            appendLine("@ApiResponses(")
            appendLine("    value = [")
            appendLine("        ApiResponse(")
            appendLine("            success = true, message = \"Successfully retrieved\",")
            appendLine("            content = [Content(schema = Schema(implementation = ApiResponse::class))],")
            appendLine("        ),")
            appendLine("        ApiResponse(success = false, message = \"$entityName not found\", data = null),")
            appendLine("    ]")
            appendLine(")")
        }
        appendLine("@GetMapping(\"/{id}\")")
        appendLine("fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<$entityName>> {")
        appendLine("    val entity = service.findById(id)")
        appendLine("    return if (entity.success) {")
        appendLine("        ResponseEntity.ok(entity)")
        appendLine("    } else {")
        appendLine("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()")
        appendLine("    }")
        appendLine("}")
    }

    val create = buildString {
        if (isSwaggerEnabled) {
            appendLine("@Operation(summary = \"Create new $entityName\", description = \"Create a new $entityName\")")
            appendLine("@ApiResponses(")
            appendLine("    value = [")
            appendLine("        ApiResponse(")
            appendLine("            success = true, message = \"Successfully created\",")
            appendLine("            content = [Content(schema = Schema(implementation = ApiResponse::class))],")
            appendLine("        ),")
            appendLine("        ApiResponse(success = false, message = \"Invalid input\"),")
            appendLine("        ApiResponse(responseCode = \"500\", description = \"Internal server error\")")
            appendLine("    ]")
            appendLine(")")
        }
        appendLine("@PostMapping")
        appendLine("fun create(@RequestBody entity: $entityName): ResponseEntity<ApiResponse<$entityName>> {")
        appendLine("    val createdEntity = service.save(entity)")
        appendLine("    return if (createdEntity.success) {")
        appendLine("        ResponseEntity.status(HttpStatus.CREATED).body(createdEntity)")
        appendLine("    } else {")
        appendLine("        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()")
        appendLine("    }")
        appendLine("}")
    }

    val update = buildString {
        if (isSwaggerEnabled) {
            appendLine("@Operation(summary = \"Update existing $entityName\", description = \"Update an existing $entityName by its ID\")")
            appendLine("@ApiResponses(")
            appendLine("    value = [")
            appendLine("        ApiResponse(")
            appendLine("            success = true, message = \"Successfully updated\",")
            appendLine("            content = [Content(schema = Schema(implementation = ApiResponse::class))],")
            appendLine("        ),")
            appendLine("        ApiResponse(success = false, message = \"$entityName not found\"),")
            appendLine("    ]")
            appendLine(")")
        }
        appendLine("@PutMapping(\"/{id}\")")
        appendLine("fun update(@PathVariable id: Long, @RequestBody entity: $entityName): ResponseEntity<ApiResponse<$entityName>> {")
        appendLine("    val updatedEntity = service.update(id, entity)")
        appendLine("    return if (updatedEntity.success) {")
        appendLine("        ResponseEntity.ok(updatedEntity)")
        appendLine("    } else {")
        appendLine("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()")
        appendLine("    }")
        appendLine("}")
    }

    val deleteById = buildString {
        if (isSwaggerEnabled) {
            appendLine("@Operation(summary = \"Delete $entityName by ID\", description = \"Delete an existing $entityName by its ID\")")
            appendLine("@ApiResponses(")
            appendLine("    value = [")
            appendLine("        ApiResponse(")
            appendLine("            success = true, message = \"Successfully deleted\",")
            appendLine("            content = [Content(schema = Schema(implementation = ApiResponse::class))],")
            appendLine("        ),")
            appendLine("        ApiResponse(success = false, message = \"$entityName not found\"),")
            appendLine("    ]")
            appendLine(")")
        }
        appendLine("@DeleteMapping(\"/{id}\")")
        appendLine("fun deleteById(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {")
        appendLine("    val deleted = service.deleteById(id)")
        appendLine("    return if (deleted.success) {")
        appendLine("        ResponseEntity.noContent().build()")
        appendLine("    } else {")
        appendLine("        ResponseEntity.status(HttpStatus.NOT_FOUND).build()")
        appendLine("    }")
        appendLine("}")
    }
    return buildString {
        appendLine("package $packageName.controller")
        appendLine(imports)
        appendLine()
        appendLine(annotations)
        appendLine("class $controllerName(private val service: $serviceName) {")
        appendLine()
        appendLine(getAll)
        appendLine()
        appendLine(getById)
        appendLine()
        appendLine(create)
        appendLine()
        appendLine(update)
        appendLine()
        appendLine(deleteById)
        appendLine("}")
    }
}