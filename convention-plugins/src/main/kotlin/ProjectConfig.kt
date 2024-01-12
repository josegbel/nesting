import org.gradle.api.JavaVersion

object ProjectConfig {
    const val projectName = "Forevely"
    const val packageName = "com.ajlabs.forevely"

    object Android {
        const val id = "$packageName.android"
        const val minSdk = 26
        const val compileSdk = 34
        const val targetSdk = compileSdk
        const val versionCode = 1
        const val versionName = "1.0"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object iOS {
        const val deploymentTarget = "15.2"
        const val summary = "Shared Module for our dating app"
        const val homepage = "Link to the Shared Module homepage"
    }

    object Kotlin {
        const val jvmTargetInt = 17
        const val jvmTarget = jvmTargetInt.toString()
        val javaVersion = JavaVersion.VERSION_17
    }
}

const val FEATURE = ".feature."
const val COMPONENTS = ".components."
