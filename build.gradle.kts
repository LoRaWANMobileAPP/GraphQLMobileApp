buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}
plugins {
    id ("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

/*tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

 */