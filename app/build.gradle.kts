plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.hilt)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.solutionplus.altasherat"
    compileSdk = 34
    flavorDimensions += "logging"

    defaultConfig {
        applicationId = "com.solutionplus.altasherat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    productFlavors {
        create("logCat") {
            dimension = "logging"
        }

        create("logWriter") {
            dimension = "logging"
        }

        create("production") {
            dimension = "logging"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    androidComponents {
        beforeVariants { variant ->
            val isReleaseWithLogCatOrLogWriterFlavor = variant.buildType == "release" &&
                    variant.productFlavors.any { it.second in listOf("logCat", "logWriter") }

            val isDebugWithProductionFlavor =
                variant.buildType == "debug" && variant.productFlavors.any { it.second == "production" }

            if (isReleaseWithLogCatOrLogWriterFlavor || isDebugWithProductionFlavor) {
                variant.enable = false
            }
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Unit Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)

    // Android DataStore
    implementation(libs.androidx.datastore.preferences)

    // Android LifeCycle
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.runtime)

    // Material
    implementation(libs.material)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)

    // SDP && SSP
    implementation(libs.intuit.sdp)
    implementation(libs.intuit.ssp)

    // GSON
    implementation(libs.google.gson)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Hilt
    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.android.compiler)

    //workManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

}