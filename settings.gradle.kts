pluginManagement {
    repositories {
        google() // tanpa content filter
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.opencv.org/maven2") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.opencv.org/maven2") }
    }
}

rootProject.name = "SIMPEL Desa"
include(":app")
 