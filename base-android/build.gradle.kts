import com.baimsg.plugin.Dep
import org.jetbrains.kotlin.kapt3.base.Kapt

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.baimsg.qstool.base.android"
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
    api(fileTree("dir" to "libs", "include" to listOf("*.aar", "*.jar")))
    api(Dep.AndroidX.Core.coreKtx)
    api(Dep.AndroidX.Core.splashscreen)
    api(Dep.AndroidX.Appcompat.appcompat)
    api(Dep.AndroidX.Appcompat.resources)
    api(Dep.AndroidX.Activity.activityCompose)

    /**
     * compose
     */
    api(Dep.Compose.ui)
    api(Dep.Compose.material)
    api(Dep.Compose.animation)
    api(Dep.Compose.animationCore)
    api(Dep.Compose.animationGraphics)
    api(Dep.Compose.uiToolingPreview)
    api(Dep.Compose.compiler)
    api(Dep.Compose.foundation)
    api(Dep.Compose.runtime)
    api(Dep.Compose.runtimeLiveData)

    /**
     * Navigation
     */
    api(Dep.AndroidX.Navigation.compose)
    api(Dep.AndroidX.Hilt.hiltNavigationCompose)

    api(Dep.Kotlin.coroutinesCore)
    /**
     * ViewModLe
     */
    api(Dep.AndroidX.LifeCycle.vmKtx)
    api(Dep.AndroidX.LifeCycle.runtimeSavedState)
    api(Dep.AndroidX.LifeCycle.vmCompose)
    api(Dep.AndroidX.LifeCycle.runtimeKtx)

    api(Dep.AndroidX.splashscreen)
    api(Dep.AndroidX.palette)
    api(Dep.AndroidX.multiDex)

    implementation(Dep.Hilt.library)
    kapt(Dep.Hilt.compiler)

    testImplementation(Dep.Libs.junit)
    androidTestImplementation(Dep.AndroidX.Test.junitKtx)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
    coreLibraryDesugaring(Dep.Libs.desugar)
}