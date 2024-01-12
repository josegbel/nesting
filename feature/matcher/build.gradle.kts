plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + FEATURE + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(projects.components.core)
            implementation(projects.components.location)

            implementation(libs.ballast.core)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
            implementation(libs.multiplatformSettings.common)
        }
    }
}
