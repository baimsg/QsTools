import com.baimsg.plugin.Dep

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.baimsg.qstool"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.baimsg.qstool"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas", "room.incremental" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            proguardFiles getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dep.AndroidX.Core.coreKtx)
    implementation(Dep.AndroidX.LifeCycle.runtimeKtx)
    implementation(Dep.AndroidX.Activity.activityCompose)
    implementation(Dep.Compose.ui)
    implementation(Dep.Compose.uiToolingPreview)
    implementation(Dep.Compose.material)
    debugImplementation(Dep.Compose.uiTooling)
//    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_ui_version"
}