import com.baimsg.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.baimsg.qstool.base"
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
    coreLibraryDesugaring(Dep.Libs.desugar)
}