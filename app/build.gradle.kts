plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlin-parcelize")
}

android {
    namespace = "com.cvindosistem.simpeldesa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cvindosistem.simpeldesa"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    // Compose
    implementation(platform(libs.androidx.compose.bom.v20240200))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)

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

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
}