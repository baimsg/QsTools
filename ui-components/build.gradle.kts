import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.baimsg.ui.components"
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dep.composeCompilerVer
        useLiveLiterals = false
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

dependencies {
    implementation(project(":base"))
    implementation(project(":base-android"))

    implementation(Dep.Hilt.library)
    kapt(Dep.Hilt.compiler)

    debugImplementation(Dep.Compose.uiTooling)
    debugImplementation(Dep.Compose.uiTestManifest)
    testImplementation(Dep.Libs.junit)
    androidTestImplementation(Dep.AndroidX.Test.junitKtx)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
    coreLibraryDesugaring(Dep.Libs.desugar)
}