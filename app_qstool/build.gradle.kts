import com.baimsg.build.fog.plugin.BytecodeFogExtension
import com.baimsg.build.fog.plugin.BytecodeFogPlugin
import com.baimsg.plugin.Dep
import java.io.ByteArrayOutputStream
import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

apply<BytecodeFogPlugin>()

the<BytecodeFogExtension>().apply {
    enable = true
    debug = true
    kg = com.baimsg.build.fog.kg.RandomKeyGenerator()
    ignoreFogClassName = com.baimsg.build.fog.annotation.BytecodeFogIgnore::class.java.name
    implementation = com.baimsg.build.fog.xor.BytecodeFogImpl::class.java.name
    fogPackages = listOf("com.baimsg")
}

fun runCommand(project: Project, command: String): String {
    val stdout = ByteArrayOutputStream()
    project.exec {
        commandLine = command.split(" ")
        standardOutput = stdout
    }
    return stdout.toString().trim { it <= ' ' }
}

val gitVersion = runCommand(project, "git rev-list HEAD --count").toIntOrNull() ?: 1

android {
    namespace = "com.baimsg.qstool"
    compileSdk = Dep.compileSdk

    compileSdkPreview = "UpsideDownCake"
    /**
     * 签名信息
     *
     */
    signingConfigs {
        val properties = Properties()
        val file = project.file("../keystore.properties")
        if (file.exists()) {
            properties.load(file.inputStream())
        }
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = file(properties.getProperty("storeFile"))
            storePassword = properties.getProperty("storePassword")
            enableV2Signing = true
            enableV1Signing = true
        }
    }

    defaultConfig {
        compileSdkPreview = "UpsideDownCake"

        applicationId = Dep.packageName
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
        versionCode = gitVersion
        versionName = Dep.version
        signingConfig = signingConfigs.getByName("release")
        multiDexEnabled = true

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
            // 启用代码压缩、优化和混淆（由R8或者ProGuard执行）
            isMinifyEnabled = true
            // 启用资源压缩（由Android Gradle plugin执行）
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    android.applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) outputFileName =
                "${defaultConfig.applicationId}_${defaultConfig.versionName}-${defaultConfig.versionCode}-${buildType.name}.apk"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }
    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dep.composeCompilerVer
        useLiveLiterals = false
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        abortOnError = true
        checkDependencies = true
        checkReleaseBuilds = false
        ignoreTestSources = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.aar", "*.jar")))
    implementation(project(":base"))
    implementation(project(":base-android"))
    implementation(project(":common-data"))
    implementation(project(":ui-resources"))
    implementation(project(":ui-theme"))
    implementation(project(":ui-components"))

    /**
     * Hilt
     */
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)
    /**
     * lifeCycle
     */
    kapt(Dep.AndroidX.LifeCycle.compiler)

    /***
     * Room
     */
    kapt(Dep.AndroidX.Room.compiler)
    testImplementation(Dep.AndroidX.Room.testing)
    /**
     * compose
     */
    debugImplementation(Dep.Compose.uiTooling)
    debugImplementation(Dep.Compose.uiTestManifest)

    testImplementation(Dep.Libs.junit)
    androidTestImplementation(Dep.AndroidX.Test.junitKtx)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
    coreLibraryDesugaring(Dep.Libs.desugar)
}