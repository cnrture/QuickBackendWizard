package com.github.cnrture.quickbackendwizard.contents

fun getApiResponseContent(
    packageName: String,
    isSwaggerEnabled: Boolean,
): String {
    return buildString {
        append("package $packageName.common\n\n")
        if (isSwaggerEnabled) {
            append("import io.swagger.v3.oas.annotations.media.Schema\n\n")
            append("@Schema(description = \"Standard API response wrapper\")\n")
        }
        append("data class ApiResponse<T>(\n")
        if (isSwaggerEnabled) {
            append("    @Schema(description = \"Success status\", example = \"true\")\n")
            append("    val success: Boolean,\n\n")
        } else {
            append("    val success: Boolean,\n")
        }
        if (isSwaggerEnabled) {
            append("    @Schema(description = \"Response message\", example = \"Operation completed successfully\")\n")
            append("    val message: String,\n\n")
        } else {
            append("    val message: String,\n")
        }
        if (isSwaggerEnabled) {
            append("    @Schema(description = \"Response data\")\n")
        }
        append("    val data: T? = null,\n")
        append(")\n")
    }
}