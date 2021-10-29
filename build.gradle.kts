import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "kotlin")

    group = "al"
    version = "0.0.1"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    configurations {
        all {
            exclude(group = "commons-logging", module = "commons-logging")
            exclude(group = "log4j", module = "log4j")
            exclude(group = "org.slf4j", module = "slf4j-log4j12")
            exclude(group = "org.slf4j", module = "slf4j-jcl")
            exclude(group = "org.slf4j", module = "slf4j-jdk14")
            exclude(group = "junit", module = "junit")
        }
    }

    dependencies {
        implementation("org.slf4j:slf4j-api:${Versions.slf4j}") //included spring-boot-starter-web
        implementation("org.slf4j:jcl-over-slf4j:${Versions.slf4j}")
        implementation("org.slf4j:log4j-over-slf4j:${Versions.slf4j}")
        implementation("org.slf4j:jul-to-slf4j:${Versions.slf4j}") //included spring-boot-starter-web
        implementation("ch.qos.logback:logback-core:${Versions.logback}") //included spring-boot-starter-web
        implementation("ch.qos.logback:logback-classic:${Versions.logback}") //included spring-boot-starter-web

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        compileOnly("org.projectlombok:lombok:${Versions.lombok}")
        testCompileOnly("org.projectlombok:lombok:${Versions.lombok}")
        annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
        testAnnotationProcessor("org.projectlombok:lombok:${Versions.lombok}")

        testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}
