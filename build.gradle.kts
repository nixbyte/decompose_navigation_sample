buildscript {

    val kotlinVersion by rootProject.extra { "1.9.10" }
    val decomposeVersion by rootProject.extra { "2.1.0" }
    val composeVersion by rootProject.extra { "1.5.3" }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:8.1.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}