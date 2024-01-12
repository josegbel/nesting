import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.apollo)
    id("com.codingfeline.buildkonfig")
    id("convention.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.kermit)
            implementation(libs.kotlin.datetime)

            implementation(libs.multiplatformSettings.common)

            implementation(libs.apollo.runtime)
            implementation(libs.apollo.normalizedCache)
            implementation(libs.apollo.normalizedCacheSqlite)
            implementation(libs.apollo.ktorEngine)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.websockets)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.test.koin)
            implementation(libs.test.kotlin.coroutines)
            implementation(libs.apollo.mockserver)
            implementation(libs.apollo.testingSupport)
            implementation(libs.test.kotlin.mockative)
        }
        androidMain.dependencies {
            implementation(libs.androidx.securityCrypto)
            implementation(libs.androidx.core)
//            implementation(libs.ktor.client.cio)
        }
        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
        }
    }
}

private val serverUrlGraphQl: String =
    getLocalProperty("server.url.graphql") ?: error("No server.url.graphql property found")

apollo {
    service(libs.versions.projectName.get()) {
        packageName.set(group.toString())
        generateFragmentImplementations.set(true)
        mapScalarToKotlinAny("Point")
//        mapScalar(
//            graphQLName = "Point",
//            targetName = "com.ajlabs.forevely.`data`.type.Point",
//            expression = "com.ajlabs.forevely.data.apollo.scalar.pointAdapter"
//        )

        introspection {
            endpointUrl.set(serverUrlGraphQl)
            val packagePath = group.toString().replace(".", "/")
            schemaFile.set(file("src/commonMain/graphql/$packagePath/schema.json"))
        }
    }
}

buildkonfig {
    packageName = group.toString()
    val dbName: String = getLocalProperty("db.name") ?: error("No db.name property found")
    val orgName: String = getLocalProperty("org.name") ?: error("No org.name property found")
    val serverUrlSubscriptions: String = getLocalProperty("server.url.subscription")
        ?: error("No server.url.subscription property found")

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "serverUrlGraphQl", serverUrlGraphQl)
        buildConfigField(FieldSpec.Type.STRING, "dbName", dbName)
        buildConfigField(FieldSpec.Type.STRING, "orgName", orgName)
        buildConfigField(FieldSpec.Type.STRING, "serverUrlSubscriptions", serverUrlSubscriptions)
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, libs.test.kotlin.mockativeProcessor)
        }
}
