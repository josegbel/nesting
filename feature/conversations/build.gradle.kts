plugins {
    id("convention.multiplatform")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

group = ProjectConfig.packageName + FEATURE + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)

            implementation(libs.ballast.core)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
            implementation(libs.paging.compose.common)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
