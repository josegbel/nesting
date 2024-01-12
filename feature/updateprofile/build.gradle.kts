plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + FEATURE + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(projects.components.core)
            implementation(projects.components.pictures)

            implementation(libs.ballast.core)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
            implementation(libs.ktor.client.core)
        }
    }
}
