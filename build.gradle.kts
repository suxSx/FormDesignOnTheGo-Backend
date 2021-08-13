import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    /* Adding FlyWay Plugin */
    id("org.flywaydb.flyway") version "7.12.1"

    /* Default Plugin */
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "cc.knoph"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

/* Fly way are implimented here. And runs on startup for Springboot as Default.*/
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    /* Temaplet manager for web page with thymeleaf */
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    /* Adding JSON */
    implementation("com.google.code.gson:gson:2.8.5")

    /* Adding Postgre SQL Dpendendecy to Gradle*/
    runtimeOnly ("org.postgresql:postgresql")

    /* Adding JPA Constructor for easy mapping to database */
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
