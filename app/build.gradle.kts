plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Plugin KSP (versi cocok dengan Kotlin 2.0)
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"

    // ✨ Tambahkan plugin Compose Compiler
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.dokka") version "1.9.10"
}


android {
    namespace = "com.cvindosistem.simpeldesa"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cvindosistem.simpeldesa"
        minSdk = 26
        targetSdk = 35
        versionCode = 5
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    tasks.dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka"))
        dokkaSourceSets {
            named("main") {
                skipEmptyPackages.set(true)
                skipDeprecated.set(true)
            }
        }
    }
}

dependencies {
    // Firebase BOM - Add this first
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Firebase libraries (versions managed by BOM)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Compose
    implementation(platform(libs.androidx.compose.bom.v20240200))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation
    implementation(libs.androidx.navigation.compose.v277)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx.v270)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v270)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Retrofit
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)
    implementation(libs.logging.interceptor.v4120)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.material)
    implementation(libs.androidx.compose.material)
    ksp(libs.androidx.room.compiler)

    // Coil
    implementation(libs.coil.compose.v250)

    // Swipe Refresh
    implementation(libs.accompanist.swiperefresh.v0320)

    // PDF Scan
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.itext7.core)
    implementation("com.google.mlkit:object-detection:17.0.2")

    implementation(libs.google.accompanist.permissions)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2.v13)
    implementation(libs.androidx.camera.lifecycle.v13)
    implementation(libs.androidx.camera.view.v13)
    implementation(libs.androidx.camera.video)

    // For photo preview
    implementation(libs.compose)
    implementation(libs.androidx.runtime.livedata.v160)

    // Kompresi
    implementation(libs.compressor)
    implementation(libs.androidx.animation)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)

    // Text Editor
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc12")
}