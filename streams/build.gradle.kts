plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.kafka:spring-kafka:3.2.3")
    implementation("org.apache.kafka:kafka-streams:3.8.0")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("org.springframework.kafka:spring-kafka-test:3.2.3")
}
