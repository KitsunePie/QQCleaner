buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val gradleVersion = "7.4.0"
    val kotlinVersion = "1.8.0"
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }
