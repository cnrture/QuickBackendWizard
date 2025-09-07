package com.github.cnrture.quickbackendwizard.contents

fun getGitIgnoreContent() = """
HELP.md
.gradle
build/
!gradle/wrapper/gradle-wrapper.jar
!**/src/main/**/build/
!**/src/test/**/build/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache
bin/
!**/src/main/**/bin/
!**/src/test/**/bin/

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### VS Code ###
.vscode/

### Kotlin ###
.kotlin

### Gradle ###
/gradle.properties

### Firebase ###
/src/main/resources/firebase-service-account.json

### Environment and Security Files ###
.env
.env.local
.env.production
.env.staging
load-env.sh
*.key
*.pem
*.p12
*.jks
*.keystore

### Database Backups ###
database_backups/
*.sql
*.sql.gz

### Deployment Scripts (if they contain sensitive data) ###
deploy-config.sh

### Application Logs ###
*.log
logs/
app.log
""".trimIndent()