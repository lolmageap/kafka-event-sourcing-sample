plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

dependencies {
    implementation(project(":redis"))
    implementation("org.flywaydb:flyway-core:10.15.0")
    implementation("io.github.oshai:kotlin-logging:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.batch:spring-batch-core:5.1.2")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

    testRuntimeOnly("com.mysql:mysql-connector-j:8.0.33")

    testImplementation("org.springframework.batch:spring-batch-test:5.1.2")
}