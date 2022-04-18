plugins {
    id("com.android.application")
    kotlin("android")
}
val composeVersion: String = "1.2.0-alpha07"

android {
    compileSdk = 32
    buildToolsVersion = "32.0.0"
    namespace = "me.kyuubiran.qqcleaner"
    defaultConfig {
        applicationId = "me.kyuubiran.qqcleaner"
        minSdk = 26
        targetSdk = 32
        versionCode = 71
        versionName = "2.0.1"
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
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

    implementation("com.github.kyuubiran:EzXHelper:0.7.5")
    compileOnly("de.robv.android.xposed:api:82")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    // 导航
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha04")
    // 虚拟键之类的适配工具
    implementation("com.google.accompanist:accompanist-insets:0.24.3-alpha")
    // 为了按钮添加的支持库
    implementation("androidx.compose.animation:animation-graphics:$composeVersion")
}
