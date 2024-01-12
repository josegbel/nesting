plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + COMPONENTS + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.gms.location)
        }
    }
}

android {
    namespace = group.toString()
}
