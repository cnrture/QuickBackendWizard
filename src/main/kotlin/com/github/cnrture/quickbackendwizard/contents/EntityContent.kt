package com.github.cnrture.quickbackendwizard.contents

fun getEntityContent(
    packageName: String,
    entityName: String,
    endpoint: String,
) = buildString {
    appendLine("package $packageName.entity")
    appendLine()
    appendLine("import jakarta.persistence.Column")
    appendLine("import jakarta.persistence.Entity")
    appendLine("import jakarta.persistence.GeneratedValue")
    appendLine("import jakarta.persistence.GenerationType")
    appendLine("import jakarta.persistence.Id")
    appendLine("import jakarta.persistence.Table")
    appendLine("import java.time.LocalDateTime")
    appendLine()
    appendLine("@Entity")
    appendLine("@Table(name = \"$endpoint\")")
    appendLine("data class $entityName(")
    appendLine("    @Id")
    appendLine("    @GeneratedValue(strategy = GenerationType.IDENTITY)")
    appendLine("    val id: Long = 0,")
    appendLine()
    appendLine("    @Column(name = \"name\")")
    appendLine("    val name: String = \"\",")
    appendLine()
    appendLine("    @Column(name = \"created_at\")")
    appendLine("    val createdAt: LocalDateTime = LocalDateTime.now(),")
    appendLine()
    appendLine("    @Column(name = \"updated_at\")")
    appendLine("    val updatedAt: LocalDateTime = LocalDateTime.now()")
    appendLine(")")
}
