plugins {
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + COMPONENTS + project.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation("androidx.exifinterface:exifinterface:1.3.6")
        }
    }
}

android {
    namespace = group.toString()
}
