import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.baimsg.ui.resources"
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }
    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.aar", "*.jar"))))
}