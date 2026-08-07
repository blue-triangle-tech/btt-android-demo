plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.safeargs.kotlin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    id("com.google.devtools.ksp")
    id("com.github.blue-triangle-tech.btt-gradle-plugin") version "1.0.0"
}

bttOptions {
    composeNavigationInjectionEnabled = true
    debugLog = true
}

android {
    namespace = "com.bluetriangle.bluetriangledemo"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.bluetriangle.bluetriangledemo"
        minSdk = 21
        targetSdk = 37
        versionCode = 16
        versionName = "2.12.0"

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
    flavorDimensions += "version"
    productFlavors {
        create("compose") {
            dimension = "version"
            applicationIdSuffix = ".compose"
            resValue("string", "app_name", "BTT e-Com")
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_compose"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_compose_round"
        }
        create("layout") {
            dimension = "version"
            applicationIdSuffix = ".layout"
            resValue("string", "app_name", "BTT e-Com")
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_layout"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_layout_round"
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
        buildConfig = true
        resValues = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

dependencies {
    implementation(libs.btt.android.sdk) {
        exclude("com.squareup.okhttp3", "okhttp-bom")
    }
    implementation(libs.core.ktx)
    implementation(libs.kotlin.parcelize.runtime)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation(libs.gson)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.hilt.android)
    implementation(libs.activity.ktx)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.moshi)

    implementation(libs.glide)
    ksp(libs.glide.ksp)
    implementation(libs.glide.compose)
    implementation(libs.okhttp3.integration)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Jetpack Compose Dependencies

    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.runtime.livedata)

    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    implementation(libs.material.android)
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
