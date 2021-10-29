plugins {
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version Versions.kotlin
}

dependencies {
    "implementation"("org.springframework.boot:spring-boot-starter-web")
    "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    "runtimeOnly"("com.h2database:h2")
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
}