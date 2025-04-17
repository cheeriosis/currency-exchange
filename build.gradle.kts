val springdocVersion = "2.8.6"
val hibernateValidatorVersion = "8.0.2.Final"
val mapStructVersion = "1.6.3"

plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.sanj.demo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.hibernate.validator:hibernate-validator:$hibernateValidatorVersion")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.testcontainers:postgresql")
}

tasks.compileJava {
    options.compilerArgs.addAll(
        listOf(
            "-Amapstruct.suppressGeneratorTimestamp=true",
            "-Amapstruct.suppressGeneratorVersionInfoComment=true",
            "-Amapstruct.defaultComponentModel=spring",
        )
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}
