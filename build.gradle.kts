import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    val kotlinVersion = "1.2.31"

    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version "1.5.10.RELEASE"
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.5")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testCompile(kotlin("test-junit"))
    testCompile("net.wuerl.kotlin:assertj-core-kotlin:0.2.1")
    testCompile("org.assertj:assertj-core:3.9.1")
    testCompile("com.nhaarman:mockito-kotlin-kt1.1:1.5.0")
    testCompile("org.mockito:mockito-core:2.18.0")
    testCompile("io.rest-assured:rest-assured:3.0.7")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    testRuntime(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
