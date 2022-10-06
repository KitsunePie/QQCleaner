buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val gradleVersion = "7.3.0"
    val kotlinVersion = "1.7.10"
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}


tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }
