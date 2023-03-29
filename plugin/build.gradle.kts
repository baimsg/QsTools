plugins {
    idea
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.10"
}

group = "com.baimsg.qstool.plugin"
version = "0.0.1"

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

gradlePlugin {
    plugins {
        create("qstool-dep") {
            id = "qstool-dep"
            implementationClass = "com.baimsg.plugin.DepPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(kotlin("gradle-plugin", version = "1.8.10"))
    implementation(kotlin("gradle-plugin-api", version = "1.8.10"))
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "11"
}