import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.baimsg.qstool.data"
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }
    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":base-android"))
    implementation(project(":ui-resources"))

    /**
     * Room数据库
     */
    api(Dep.AndroidX.Room.roomKtx)
    api(Dep.AndroidX.Room.common)
    api(Dep.AndroidX.Room.runtime)
    api(Dep.AndroidX.Room.paging)
    kapt(Dep.AndroidX.Room.compiler)
    testImplementation(Dep.AndroidX.Room.testing)
    /**
     * hilt
     */
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)

    coreLibraryDesugaring(Dep.Libs.desugar)
}