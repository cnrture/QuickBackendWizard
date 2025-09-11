package com.github.cnrture.quickbackendwizard.contents

fun getRepositoryContent(
    packageName: String,
    entityName: String,
    repositoryName: String,
) = """
package $packageName.repository

import $packageName.entity.$entityName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface $repositoryName : JpaRepository<$entityName, Long> {
    // TODO: Add custom query methods here
}
""".trimIndent()