buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val gradleVersion = "7.1.2"
    val kotlinVersion = "1.6.10"
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}


tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }
