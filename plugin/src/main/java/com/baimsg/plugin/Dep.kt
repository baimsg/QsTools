package com.baimsg.plugin

import org.gradle.api.JavaVersion

/**
 * Create by Baimsg on 2023/3/29
 *
 **/
object Dep {

    val javaVersion = JavaVersion.VERSION_11
    const val kotlinJvmTarget = "11"
    const val kotlinVer = "1.8.10"
    const val androidGradleVer = "7.4.2"
    const val compileSdk = 33
    const val minSdk = 21
    const val targetSdk = 33
    const val group = "com.baimsg.qstool"
    const val packageName = "com.baimsg.qstool"
    const val version = "1.0.0"

    const val composeCompilerVer = "1.4.3"

    object Compose {
        private const val composeVerLow = "1.3.1"
        private const val composeVerUp = "1.3.3"

        const val compiler = "androidx.compose.compiler:compiler:$composeCompilerVer"
        const val animation = "androidx.compose.animation:animation:$composeVerUp"
        const val animationCore = "androidx.compose.animation:animation-core:$composeVerUp"
        const val animationGraphics = "androidx.compose.animation:animation-graphics:$composeVerUp"
        const val foundation = "androidx.compose.foundation:foundation:$composeVerLow"
        const val material = "androidx.compose.material:material:$composeVerLow"
        const val material3 = "androidx.compose.material3:material3:1.0.1"
        const val runtime = "androidx.compose.runtime:runtime:$composeVerUp"
        const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:$composeVerUp"
        const val runtimeRxJava2 = "androidx.compose.runtime:runtime-rxjava2:$composeVerUp"
        const val ui = "androidx.compose.ui:ui:$composeVerUp"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVerUp"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVerUp"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVerUp"
        const val uiUtil = "androidx.compose.ui:ui-util:$composeVerUp"

    }

    object Accompanist {
        private const val accompanist_version = "0.30.0"
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:$accompanist_version"
        const val themeAdapterAppcompat =
            "com.google.accompanist:accompanist-themeadapter-appcompat:$accompanist_version"
        const val themeAdapterMaterial =
            "com.google.accompanist:accompanist-themeadapter-material:$accompanist_version"
        const val themeAdapterMaterial3 =
            "com.google.accompanist:accompanist-themeadapter-material3:$accompanist_version"
        const val permissions =
            "com.google.accompanist:accompanist-permissions:$accompanist_version"
        const val placeholder =
            "com.google.accompanist:accompanist-placeholder:$accompanist_version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$accompanist_version"
        const val navigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:$accompanist_version"
        const val navigationMaterial =
            "com.google.accompanist:accompanist-navigation-material:$accompanist_version"
        const val drawablePainter =
            "com.google.accompanist:accompanist-drawablepainter:$accompanist_version"
        const val webView = "com.google.accompanist:accompanist-webview:$accompanist_version"
        const val adaptive = "com.google.accompanist:accompanist-adaptive:$accompanist_version"
        const val testHarness =
            "com.google.accompanist:accompanist-testharness:$accompanist_version"
        const val insets = "com.google.accompanist:accompanist-insets:$accompanist_version"
        const val swipeRefresh =
            "com.google.accompanist:accompanist-swiperefresh:$accompanist_version"
        const val appcompatTheme =
            "com.google.accompanist:accompanist-appcompat-theme:$accompanist_version"
        const val pager = "com.google.accompanist:accompanist-pager:$accompanist_version"
    }

    object AndroidX {

        const val multiDex = "androidx.multidex:multidex:2.0.1"
        const val exifInterface = "androidx.exifinterface:exifinterface:1.3.6"
        const val documentFile = "androidx.documentfile:documentfile:1.0.1"
        const val interpolator = "androidx.interpolator:interpolator:1.0.0"
        const val draganddrop = "androidx.draganddrop:draganddrop:1.0.0"
        const val autofill = "androidx.autofill:autofill:1.1.0"
        const val splashscreen = "androidx.core:core-splashscreen:1.0.0"
        const val palette = "androidx.palette:palette-ktx:1.0.0"
        const val contentPager = "androidx.contentpager:contentpager:1.0.0"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val cardView = "aandroidx.cardview:cardview:1.0.0"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:1.0.0"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:1.1.1"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val recyclerviewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Activity {
            private const val ver = "1.6.1"
            const val activityJava = "androidx.activity:activity:$ver"
            const val activityKt = "androidx.activity:activity-ktx:$ver"
            const val activityCompose = "androidx.activity:activity-compose:$ver"
        }

        object Fragment {
            private const val fragment_version = "1.5.5"
            const val fragment = "androidx.fragment:fragment:$fragment_version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragment_version"
            const val testing = "androidx.fragment:fragment-testing:$fragment_version"
        }

        object ConstraintLayout {
            const val library = "androidx.constraintlayout:constraintlayout:2.1.4"
            const val core = "androidx.constraintlayout:constraintlayout-core:1.0.4"
            const val compose = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        }

        object Appcompat {
            private const val appcompat_version = "1.6.1"
            const val appcompat = "androidx.appcompat:appcompat:$appcompat_version"

            // For loading and tinting drawables on older versions of the platform
            const val resources = "androidx.appcompat:appcompat-resources:$appcompat_version"
        }

        object Core {
            private const val core_version = "1.9.0"
            const val core = "androidx.core:core:$core_version"
            const val coreKtx = "androidx.core:core-ktx:$core_version"
            const val role = "androidx.core:core-role:1.0.0"
            const val animation = "androidx.core:core-animation:1.0.0-beta01"
            const val animationTesting = "androidx.core:core-animation-testing:1.0.0-beta01"
            const val performance = "androidx.core:core-performance:1.0.0-alpha02"
            const val shortcuts = "androidx.core:core-google-shortcuts:1.1.0"
            const val remoteViews = "androidx.core:core-remoteviews:1.0.0-beta03"
            const val splashscreen = "androidx.core:core-splashscreen:1.1.0-alpha01"
        }

        object Collection {
            private const val collection_version = "1.2.0"
            const val collection = "androidx.collection:collection:$collection_version"
            const val collectionKtx = "androidx.collection:collection-ktx:$collection_version"
        }

        object Concurrent {
            private const val concurrent_version = "1.1.0"
            const val futures = "androidx.concurrent:concurrent-futures:$concurrent_version"
            const val futuresKtx = "androidx.concurrent:concurrent-futures-ktx:$concurrent_version"
        }

        object Hilt {
            private const val hilt_version = "1.0.0"
            const val hilt = "androidx.hilt:hilt:$hilt_version"
            const val work = "androidx.hilt:hilt-work:$hilt_version"
            const val hiltNavigation = "androidx.hilt:hilt-navigation:$hilt_version"
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hilt_version"
        }

        object LifeCycle {
            private const val lifecycle_version = "2.5.1"
            private const val arch_version = "2.1.0"
            const val vmKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
            const val vmCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
            const val runtimeSavedState =
                "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"

            /**
             * kapt
             */
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
            const val service = "androidx.lifecycle:lifecycle-service:$lifecycle_version"
            const val process = "androidx.lifecycle:lifecycle-process:$lifecycle_version"
            const val reactivestreamsKtx =
                "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"

            /**
             * testImplementation
             */
            const val coreTesting = "androidx.arch.core:core-testing:$arch_version"
            const val runtimeTesting =
                "androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version"
        }

        object DynamicAnimation {
            const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:1.0.0"
            const val dynamicAnimationKtx = "androidx.dynamicanimation:dynamicanimation-ktx:1.0.0"
        }

        object Camera {
            private const val camerax_version = "1.2.1"

            // The following line is optional, as the core library is included indirectly by camera-camera2
            const val core = "androidx.camera:camera-core:$camerax_version"
            const val camera2 = "androidx.camera:camera-camera2:$camerax_version"

            // If you want to additionally use the CameraX Lifecycle library
            const val lifecycle = "androidx.camera:camera-lifecycle:$camerax_version"

            // If you want to additionally use the CameraX VideoCapture library
            const val video = "androidx.camera:camera-video:$camerax_version"

            // If you want to additionally use the CameraX View class
            const val view = "androidx.camera:camera-view:$camerax_version"

            // If you want to additionally add CameraX ML Kit Vision Integration
            const val mlKitVision = "androidx.camera:camera-mlkit-vision:$camerax_version"

            // If you want to additionally use the CameraX Extensions library
            const val extensions = "androidx.camera:camera-extensions:$camerax_version"
        }

        object Biometric {
            const val biometric = "androidx.biometric:biometric:1.1.0"
            const val biometricKtx = "androidx.biometric:biometric-ktx:1.1.0"
        }

        object Emoji {
            private const val emoji_version = "1.1.0"
            const val emoji = "androidx.emoji:emoji:$emoji_version"
            const val appcompat = "androidx.emoji:emoji-appcompat:$emoji_version"
            const val bundled = "androidx.emoji:emoji-bundled:$emoji_version"
        }

        object Emoji2 {
            private const val emoji2_version = "1.3.0"
            const val emoji2 = "androidx.emoji2:emoji2:$emoji2_version"
            const val views = "androidx.emoji2:emoji2-views:$emoji2_version"
            const val viewsHelper = "androidx.emoji2:emoji2-views-helper:$emoji2_version"
        }

        object Media2 {
            private const val media2_version = "1.2.1"
            const val session = "androidx.media2:media2-session:$media2_version"
            const val widget = "androidx.media2:media2-widget:$media2_version"
            const val player = "androidx.media2:media2-player:$media2_version"
        }

        object Media3 {
            private const val media3_version = "1.0.0-rc01"

            // For media playback using ExoPlayer
            const val exoplayer = "androidx.media3:media3-exoplayer:$media3_version"

            // For DASH playback support with ExoPlayer
            const val exoplayerDash = "androidx.media3:media3-exoplayer-dash:$media3_version"

            // For HLS playback support with ExoPlayer
            const val exoplayerHls = "androidx.media3:media3-exoplayer-hls:$media3_version"

            // For RTSP playback support with ExoPlayer
            const val exoplayerRtsp = "androidx.media3:media3-exoplayer-rtsp:$media3_version"

            // For ad insertion using the Interactive Media Ads SDK with ExoPlayer
            const val exoplayerIma = "androidx.media3:media3-exoplayer-ima:$media3_version"

            // For loading data using the Cronet network stack
            const val datasourceCronet = "androidx.media3:media3-datasource-cronet:$media3_version"

            // For loading data using the OkHttp network stack
            const val datasourceOkhttp = "androidx.media3:media3-datasource-okhttp:$media3_version"

            // For loading data using librtmp
            const val datasourceRtmp = "androidx.media3:media3-datasource-rtmp:$media3_version"

            // For building media playback UIs
            const val ui = "androidx.media3:media3-ui:$media3_version"

            // For building media playback UIs for Android TV using the Jetpack Leanback library
            const val uiLeanback = "androidx.media3:media3-ui-leanback:$media3_version"

            // For exposing and controlling media sessions
            const val session = "androidx.media3:media3-session:$media3_version"

            // For extracting data from media containers
            const val extractor = "androidx.media3:media3-extractor:$media3_version"

            // For integrating with Cast
            const val cast = "androidx.media3:media3-cast:$media3_version"

            // For scheduling background operations using Jetpack Work's WorkManager with ExoPlayer
            const val workManager = "androidx.media3:media3-exoplayer-workmanager:$media3_version"

            // For transforming media files
            const val transformer = "androidx.media3:media3-transformer:$media3_version"

            // Utilities for testing media components (including ExoPlayer components)
            const val testUtils = "androidx.media3:media3-test-utils:$media3_version"

            // Utilities for testing media components (including ExoPlayer components) via Robolectric
            const val testUtilsRobolectric =
                "androidx.media3:media3-test-utils-robolectric:$media3_version"

            // Common functionality for media database components
            const val database = "androidx.media3:media3-database:$media3_version"

            // Common functionality for media decoders
            const val decoder = "androidx.media3:media3-decoder:$media3_version"

            // Common functionality for loading data
            const val datasource = "androidx.media3:media3-datasource:$media3_version"

            // Common functionality used across multiple media libraries
            const val common = "androidx.media3:media3-common:$media3_version"
        }

        object Navigation {
            private const val nav_version = "2.5.3"

            /**
             * Java
             */
            const val fragment = "androidx.navigation:navigation-fragment:$nav_version"
            const val ui = "androidx.navigation:navigation-ui:$nav_version"

            /**
             * Kotlin
             */
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$nav_version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$nav_version"
            const val features =
                "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

            /**
             * androidTestImplementation
             */
            const val testing = "androidx.navigation:navigation-testing:$nav_version"
            const val compose = "androidx.navigation:navigation-compose:$nav_version"
        }

        object CustomView {
            private const val custom_view_version = "1.1.0"
            const val view = "androidx.customview:customview:$custom_view_version"
            const val poolingContainer =
                "androidx.customview:customview-poolingcontainer:$custom_view_version"
        }

        object DataStore {
            private const val data_store_version = "1.0.0"

            /**
             * Preferences DataStore
             */
            const val preferences = "androidx.datastore:datastore-preferences:1.0.0"
            const val preferencesCore = "androidx.datastore:datastore-preferences-core:1.0.0"
            const val preferencesRxJava2 = "androidx.datastore:datastore-preferences-rxjava2:1.0.0"
            const val preferencesRxJava3 = "androidx.datastore:datastore-preferences-rxjava3:1.0.0"

            /**
             * Proto DataStore
             */
            const val proto = "androidx.datastore:datastore:1.0.0"
            const val protoCore = "androidx.datastore:datastore-core:1.0.0"
            const val protoRxJava2 = "androidx.datastore:datastore-rxjava2:1.0.0"
            const val protoRxJava3 = "androidx.datastore:datastore-rxjava3:1.0.0"


        }

        object Paging {
            private const val paging_version = "3.1.1"
            const val runtime = "androidx.paging:paging-runtime:$paging_version"

            /**
             * testImplementation
             */
            const val common = "androidx.paging:paging-common:$paging_version"
            const val rxJava2 = "androidx.paging:paging-rxjava2:$paging_version"
            const val rxJava3 = "androidx.paging:paging-rxjava3:$paging_version"
            const val guava = "androidx.paging:paging-guava:$paging_version"
            const val compose = "androidx.paging:paging-compose:1.0.0-alpha18"
        }

        object Room {
            private const val room_version = "2.5.0"
            const val runtime = "androidx.room:room-runtime:$room_version"
            const val compiler = "androidx.room:room-compiler:$room_version"
            const val roomKtx = "androidx.room:room-ktx:$room_version"
            const val rxjava2 = "androidx.room:room-rxjava2:$room_version"
            const val rxjava3 = "androidx.room:room-rxjava3:$room_version"
            const val guava = "androidx.room:room-guava:$room_version"
            const val testing = "androidx.room:room-testing:$room_version"
            const val paging = "androidx.room:room-paging:$room_version"
        }

        object SavedState {
            const val savedState = "androidx.savedstate:savedstate:1.2.1"
            const val savedStateKtx = "androidx.savedstate:savedstate-ktx:1.2.1"
        }

        object Work {
            private const val work_version = "2.8.0"
            const val runtime = "androidx.work:work-runtime:$work_version"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$work_version"
            const val runtimeRxJava2 = "androidx.work:work-rxjava2:$work_version"
            const val runtimeGmc = "androidx.work:work-gcm:$work_version"
            const val runtimeTesting = "androidx.work:work-testing:$work_version"
            const val runtimeMultiProcess = "androidx.work:work-multiprocess:$work_version"
        }

        object Webkit {
            const val webkit = "androidx.webkit:webkit:1.6.0"
        }

        object Test {
            private const val test_version = "1.5.0"
            const val core = "androidx.test:core:$test_version"
            const val coreKtx = "androidx.test:core-ktx:$test_version"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
            const val junit = "androidx.test.ext:junit:1.1.5"
            const val junitKtx = "androidx.test.ext:junit-ktx:1.1.5"
            const val truth = "androidx.test.ext:truth:1.5.0"
            const val runner = "androidx.test:runner:1.5.2"
            const val orchestrator = "androidx.test:orchestrator:1.4.2"
            const val monitor = "androidx.test:monitor:1.6.1"
            const val rules = "androidx.test:rules:1.5.0"
            const val services = "androidx.test.services:test-services:1.4.2"
            const val servicesStorage = "androidx.test.services:storage:1.4.2"
            const val annotation = "androidx.test:annotation:1.0.1"

        }

    }

    object Kotlin {
        private const val coroutines = "1.6.4"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVer"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVer"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0"
    }


    object Retrofit {
        private const val retrofit = "2.9.0"
        const val library = "com.squareup.retrofit2:retrofit:$retrofit"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofit"
        const val kotlinSerializerConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object OkHttp {
        private const val okhttp = "4.10.0"
        const val library = "com.squareup.okhttp3:okhttp:$okhttp"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttp"
    }

    object Lottie {
        private const val ver = "5.2.0"
        const val library = "com.airbnb.android:lottie:$ver"
        const val compose = "com.airbnb.android:lottie-compose:$ver"
    }

    object Coil {
        private const val coil_version = "2.3.0"
        const val library = "io.coil-kt:coil:$coil_version"
        const val base = "io.coil-kt:coil-base:$coil_version"
        const val gif = "io.coil-kt:coil-gif:$coil_version"
        const val svg = "io.coil-kt:coil-svg:$coil_version"
        const val video = "io.coil-kt:coil-video:$coil_version"
        const val test = "io.coil-kt:coil-test:$coil_version"
        const val bom = "io.coil-kt:coil-bom:$coil_version"
        const val compose = "io.coil-kt:coil-compose:$coil_version"
        const val composeBase = "io.coil-kt:coil-compose-base:$coil_version"
    }

    object Hilt {
        private const val hilt_version = "2.43.2"
        const val hilt = "com.google.dagger:hilt-android:$hilt_version"
        const val compiler = "com.google.dagger:hilt-compiler:$hilt_version"
    }


    //第三方库
    object Libs {
        const val mmkv = "com.tencent:mmkv-static:1.2.14"
        const val store = "com.dropbox.mobile.store:store4:4.0.5"
        const val desugar = "com.android.tools:desugar_jdk_libs:1.1.5"
        const val junit = "junit:junit:4.13.2"
    }


}