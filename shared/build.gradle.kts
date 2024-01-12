import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.compose)
    alias(libs.plugins.maps.secrets)
    kotlin("multiplatform")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

group = libs.versions.projectPackage.get() + "." + project.name

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
@Suppress("unused")
kotlin {
    applyDefaultHierarchyTemplate()
    jvmToolchain(libs.versions.javaVersion.get().toInt())

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "A Kotlin Multiplatform library for Forevely"
        homepage = "forevely.com"
        version = "1.0"
        ios.deploymentTarget = "12.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = project.name
            isStatic = false
            linkerOpts.add("-lsqlite3")
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += listOf("-Xdisable-phases=VerifyBitcode", "-Xexpect-actual-classes")
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += listOf("-Xopt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(projects.components.core)
            implementation(projects.components.matcherCards)

            implementation(projects.feature.debug)
            implementation(projects.feature.root)
            implementation(projects.feature.router)
            implementation(projects.feature.login)
            implementation(projects.feature.conversations)
            implementation(projects.feature.chat)
            implementation(projects.feature.matcher)
            implementation(projects.feature.onboarding)
            implementation(projects.feature.updateprofile)
            implementation(projects.feature.myplan)

            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.haze.common)

            implementation(libs.insetsx)

            implementation(libs.ktor.client.core)

            implementation(libs.paging.compose.common)
            implementation(libs.koin.compose)
            implementation(libs.kotlin.datetime)
            implementation(libs.kermit)
            implementation(libs.ballast.core)
            implementation(libs.kamel.image)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.koin.androidxCompose)
            implementation(libs.androidx.gms.location)
            implementation(libs.androidx.gms.playServicesMaps)
            implementation(libs.maps.compose)
            implementation(libs.coil)
        }
    }
}

android {
    namespace = libs.versions.projectPackage.get()
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = namespace
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = compileSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    kotlin {
        jvmToolchain(libs.versions.javaVersion.get().toInt())
    }
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
}
