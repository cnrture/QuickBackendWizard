package com.github.cnrture.quickbackendwizard.contents

fun getApiResponseContent(
    packageName: String,
    isSwaggerEnabled: Boolean,
): String {
    return buildString {
        appendLine("package $packageName.common")
        appendLine()
        if (isSwaggerEnabled) {
            appendLine("import io.swagger.v3.oas.annotations.media.Schema")
            appendLine()
            appendLine("@Schema(description = \"Standard API response wrapper\")")
        }
        appendLine("data class ApiResponse<T>(")
        if (isSwaggerEnabled) {
            appendLine("    @Schema(description = \"Success status\", example = \"true\")")
            appendLine("    val success: Boolean,")
            appendLine()
        } else {
            appendLine("    val success: Boolean,")
        }
        if (isSwaggerEnabled) {
            appendLine("    @Schema(description = \"Response message\", example = \"Operation completed successfully\")")
            appendLine("    val message: String,")
            appendLine()
        } else {
            appendLine("    val message: String,")
        }
        if (isSwaggerEnabled) {
            appendLine("    @Schema(description = \"Response data\")")
        }
        appendLine("    val data: T? = null,")
        appendLine(")")
    }
}