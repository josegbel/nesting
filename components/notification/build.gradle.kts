plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + COMPONENTS + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.core)
            implementation(libs.coroutines.core)
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.uuid)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
        }
    }
}

android {
    namespace = group.toString()
}
