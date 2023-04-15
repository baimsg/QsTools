plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.10"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:8.0.0")
    implementation(kotlin("stdlib"))
    implementation("org.ow2.asm:asm-util:9.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.squareup:javawriter:2.5.1")
    implementation("com.google.guava:guava:31.1-jre")
    gradleApi()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
