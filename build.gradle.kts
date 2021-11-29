plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    id("nu.studer.jooq") version "6.0.1" apply false
    `kotlin-dsl`
    application
}

group = "org.xendv.java.edumail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // server jetty
    implementation("org.eclipse.jetty:jetty-servlet:9.4.33.v20201020")
    implementation("org.eclipse.jetty:jetty-server:9.4.33.v20201020")

    // db
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.9")

    // compile
    compile("org.jooq:jooq:3.15.4")
    // jooq
    implementation("org.jooq:jooq:3.15.4")
    implementation("org.jooq:jooq-codegen:3.15.4")
    implementation("org.jooq:jooq-meta:3.15.4")
    implementation(project(":jooq-generated"))

    // annotations
    implementation ("org.jetbrains:annotations:13.0")
    implementation("org.projectlombok:lombok:1.18.4")
    annotationProcessor("org.projectlombok:lombok:1.18.4")

}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    group = "org.xendv.java.edumail"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        // compile
        compile("org.jooq:jooq:3.15.4")

        // annotations
        implementation ("org.jetbrains:annotations:13.0")
        implementation("org.projectlombok:lombok:1.18.4")
        annotationProcessor("org.projectlombok:lombok:1.18.4")

        // db
        implementation("org.flywaydb:flyway-core:8.0.1")
        implementation("org.postgresql:postgresql:42.2.9")
        // jooq
        implementation("org.jooq:jooq:3.15.4")
        implementation("org.jooq:jooq-codegen:3.15.4")
        implementation("org.jooq:jooq-meta:3.15.4")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}