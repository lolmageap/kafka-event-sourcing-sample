plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

dependencies {
    implementation("org.redisson:redisson-spring-data-27:3.25.2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("io.github.oshai:kotlin-logging:5.1.0")
}