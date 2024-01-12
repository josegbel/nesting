import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.detekt)
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover.get()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.buildkonfig.gradle.plugin)
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<KtlintExtension> {
        filter {
            exclude { element -> element.file.path.contains("/build/") }
        }
        debug.set(false)
        outputToConsole.set(true)
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        parallel = true
        config.setFrom(files(rootProject.file("detekt.yml")))
        autoCorrect = true
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = libs.versions.javaVersion.get()
        parallel = true
        reports {
            xml.required.set(false)
            html.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
        }
        exclude { it.file.absolutePath.contains("resources/") }
        exclude { it.file.absolutePath.contains("build/") }
        include("**/*.kt")
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach {
        this.jvmTarget = libs.versions.javaVersion.get()
        exclude { it.file.absolutePath.contains("resources/") }
        exclude { it.file.absolutePath.contains("build/") }
        include("**/*.kt")
    }

    tasks.register("detektAll") {
        group = "verification"
        description = "Runs all detekt tasks"
        dependsOn(tasks.withType<Detekt>())
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = libs.versions.javaVersion.get()
    }
}
