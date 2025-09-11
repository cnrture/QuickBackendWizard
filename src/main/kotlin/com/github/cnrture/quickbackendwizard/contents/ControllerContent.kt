package com.github.cnrture.quickbackendwizard.contents

import com.github.cnrture.quickbackendwizard.generators.EndpointInfo

fun getControllerContent(
    packageName: String,
    entityName: String,
    controllerName: String,
    serviceName: String,
    endpoint: EndpointInfo,
) = """
package $packageName.controller

import $packageName.entity.$entityName
import $packageName.service.$serviceName
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${endpoint.name}")
class $controllerName(private val service: $serviceName) {

    @GetMapping
    fun getAll(): ResponseEntity<List<$entityName>> = 
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<$entityName> = 
        ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun create(@RequestBody entity: $entityName): ResponseEntity<$entityName> = 
        ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: $entityName): ResponseEntity<$entityName> = 
        ResponseEntity.ok(service.update(id, entity))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
""".trimIndent()