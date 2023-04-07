import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.baimsg.qstool.base"
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }

    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }
}

java {
    sourceCompatibility = Dep.javaVersion
    targetCompatibility = Dep.javaVersion
}

dependencies {
    api(fileTree("dir" to "libs", "include" to listOf("*.aar", "*.jar")))
    /**
     * Kotlin
     */
    api(Dep.Kotlin.stdlib)
    api(Dep.Kotlin.serializationJson)
    api(Dep.Kotlin.coroutinesCore)
    /**
     * 网络框架
     */
    api(Dep.Retrofit.library)
    api(Dep.Retrofit.kotlinSerializerConverter)
    api(Dep.OkHttp.library)
    api(Dep.OkHttp.loggingInterceptor)

    //其他常用sdk
    api(Dep.Libs.mmkv)
    api(Dep.Libs.store)

    api(project(":bytecode-fog-ext"))

    testImplementation(Dep.Libs.junit)
    androidTestImplementation(Dep.AndroidX.Test.junitKtx)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
    coreLibraryDesugaring(Dep.Libs.desugar)
}