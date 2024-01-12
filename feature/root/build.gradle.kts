plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + FEATURE + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(projects.feature.chat)
            implementation(projects.components.notification)

            implementation(libs.ballast.core)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
        }
    }
}
