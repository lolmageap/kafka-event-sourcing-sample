plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    kotlin("plugin.jpa") version "2.0.10"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":redis"))
    implementation("org.redisson:redisson-spring-data-27:3.25.2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka:3.2.3")
    implementation("io.github.oshai:kotlin-logging:5.1.0")

    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

    testAndDevelopmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testRuntimeOnly("com.mysql:mysql-connector-j:8.0.33")

    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}