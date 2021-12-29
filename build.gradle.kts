buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val gradleVersion : String by project
    val kotlinVersion : String by project
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}


tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }
