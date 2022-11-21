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
        minSdk = 24
        targetSdk = 33
        versionCode = 100
        versionName = "3.0.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    viewBinding {
        enable = true
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
tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

dependencies {
//    implementation(files("./libs/EzXHelper-release.aar"))
    implementation(project(":RCLayout"))
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")

    val lifecycleVersion = "2.6.0-alpha02"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // 引入 androidx 的 fragment 作为页面承接
    implementation("androidx.fragment:fragment-ktx:1.6.0-alpha03")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    // 阴影
    implementation("com.github.lihangleo2:ShadowLayout:3.2.4")
    implementation("com.github.kyuubiran:EzXHelper:1.0.3")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    compileOnly("de.robv.android.xposed:api:82")
}
