plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + FEATURE + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ballast.core)
            api(libs.ballast.navigation)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
            implementation(libs.coroutines.core)
        }
    }
}
