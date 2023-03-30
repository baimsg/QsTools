import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk
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
    coreLibraryDesugaring(Dep.Libs.desugar)
}