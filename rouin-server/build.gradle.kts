plugins {
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version Versions.kotlin
    kotlin("plugin.jpa") version Versions.kotlin
}

dependencies {
    "implementation"("org.springframework.boot:spring-boot-starter-web")
    "implementation"("org.springframework.boot:spring-boot-starter-data-jpa")
    "implementation"("org.springframework.boot:spring-boot-starter-webflux")

    "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    "implementation"("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}")

    implementation("mysql:mysql-connector-java:8.0.27")

    //bank library
    "implementation"("com.plaid:plaid-java:9.5.0")

    "runtimeOnly"("com.h2database:h2:1.4.200")

    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
}