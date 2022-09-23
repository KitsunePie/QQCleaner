@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"
    namespace = "me.kyuubiran.qqcleaner"
    defaultConfig {
        applicationId = "me.kyuubiran.qqcleaner"
        minSdk = 26
        targetSdk = 33
        versionCode = 100
        versionName = "3.0.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(listOf("proguard-rules.pro"))
        }
    }

    androidResources {
        additionalParameters("--preferred-density", "xxxhdpi")
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x63")
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/**", "kotlin/**", "google/**", "**.bin"))
    }

    dependenciesInfo {
        includeInApk = false
    }
}

dependencies {
//    implementation(files("./libs/EzXHelper-release.aar"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    implementation("com.github.kyuubiran:EzXHelper:1.0.3")
    compileOnly("de.robv.android.xposed:api:82")
}
