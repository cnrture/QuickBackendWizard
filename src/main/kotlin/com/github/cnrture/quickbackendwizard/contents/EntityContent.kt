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

@Entity
@Table(name = "${endpoint.name}")
data class $entityName(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String = ""
)
""".trimIndent()