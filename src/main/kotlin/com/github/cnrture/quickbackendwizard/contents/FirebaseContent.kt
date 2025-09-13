package com.github.cnrture.quickbackendwizard.contents

fun getFirebaseConfigContent(
    packageName: String,
): String {
    return buildString {
        appendLine("package $packageName.config")
        appendLine()
        appendLine("import com.google.auth.oauth2.GoogleCredentials")
        appendLine("import com.google.firebase.FirebaseApp")
        appendLine("import com.google.firebase.FirebaseOptions")
        appendLine("import org.springframework.context.annotation.Configuration")
        appendLine("import org.springframework.core.io.ClassPathResource")
        appendLine("import javax.annotation.PostConstruct")
        appendLine()
        appendLine("@Configuration")
        appendLine("class FirebaseConfig {")
        appendLine()
        appendLine("    @PostConstruct")
        appendLine("    fun initializeFirebase() {")
        appendLine("        if (FirebaseApp.getApps().isEmpty()) {")
        appendLine("            try {")
        appendLine("                // Load the service account key JSON file from the resources folder")
        appendLine("                val serviceAccount = ClassPathResource(\"firebase-service-account.json\").inputStream")
        appendLine("                val options = FirebaseOptions.builder()")
        appendLine("                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))")
        appendLine("                    .build()")
        appendLine()
        appendLine("                FirebaseApp.initializeApp(options)")
        appendLine("                println(\"Firebase Admin SDK initialized successfully\")")
        appendLine("            } catch (e: Exception) {")
        appendLine("                println(\"Failed to initialize Firebase Admin SDK: \${e.message}\")")
        appendLine("                throw e")
        appendLine("            }")
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
    }
}