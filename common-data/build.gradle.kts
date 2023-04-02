import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.baimsg.qstool.data"
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = Dep.javaVersion
        targetCompatibility =  Dep.javaVersion
    }
    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }
}

dependencies {

    implementation("androidx.core:core-ktx:+")
}