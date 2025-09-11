package com.github.cnrture.quickbackendwizard.contents

import com.github.cnrture.quickbackendwizard.generators.EndpointInfo

fun getEntityContent(
    packageName: String,
    entityName: String,
    endpoint: EndpointInfo,
) = """
package $packageName.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "${endpoint.name}")
data class $entityName(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
""".trimIndent()
