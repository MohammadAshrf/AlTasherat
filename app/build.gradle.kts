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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.activity)

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
    kapt(libs.hilt.compiler)
    kapt(libs.google.hilt.android.compiler)

    //workManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Mockito
    androidTestImplementation("org.mockito:mockito-core:3.11.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("io.mockk:mockk:1.12.0")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:runner:1.4.0")

    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.12.0") // MockK
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test:runner:1.4.0")
    implementation("com.google.android.material:material:1.2.0")


    // Dots Indicator
    implementation(libs.dotsindicator)
    implementation(kotlin("test"))

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //ThreeTenABP for the date
    implementation (libs.threetenabp)
}