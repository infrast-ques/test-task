plugins {
    kotlin("jvm") version "1.9.23"
    id("io.freefair.aspectj.post-compile-weaving") version "8.6"
}

group = "my"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.9.23")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    implementation("io.rest-assured:rest-assured:5.5.0")
    implementation("org.java-websocket:Java-WebSocket:1.5.7")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.awaitility:awaitility-kotlin:4.2.2")
    testImplementation("org.assertj:assertj-core:3.26.3")

    testImplementation(platform("io.qameta.allure:allure-bom:2.29.1"))
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    implementation("io.qameta.allure:allure-rest-assured:2.29.1")
//    implementation("io.qameta.allure:allure-java-commons:2.29.1")
    agent("org.aspectj:aspectjweaver:1.9.21")
    implementation("org.aspectj:aspectjrt:1.9.21")
    implementation("org.aspectj:aspectjweaver:1.9.21")

    implementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    implementation("org.junit.jupiter:junit-jupiter:5.11.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:ignore")
}

kotlin {
    jvmToolchain(21)
}