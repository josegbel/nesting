plugins {
    alias(libs.plugins.compose)
    id("convention.multiplatform")
}

group = ProjectConfig.packageName + "." + project.name.replace("-", "")

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.material)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.materialIconsExtended)
        }
    }
}

android {
    namespace = group.toString()
}
