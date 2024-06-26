

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.crealite.crealiteapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crealite.crealiteapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("androidx.activity:activity:1.6.0-alpha05")
    implementation(libs.legacy.support.v4)
    implementation(libs.filament.android)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}