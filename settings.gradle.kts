enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.4.0")
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        maven { url = uri("https://jitpack.io") }
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Forevely-Client"

includeBuild("convention-plugins")
include(
    ":shared",
    ":components:matcher-cards",
    ":components:core",
    ":components:location",
    ":components:notification",
    ":components:pictures",

    ":data",

    ":feature:debug", // To remove on release
    ":feature:conversations",
    ":feature:chat",
    ":feature:login",
    ":feature:matcher",
    ":feature:updateprofile",
    ":feature:myplan",
    ":feature:onboarding",
    ":feature:root",
    ":feature:router",
    ":feature:updateprofile",
)
