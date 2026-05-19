plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ktlint.gradle)
}

android {
    namespace = "tenfen.rodolfo.moisesaichallenge"

    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt())
    }

    defaultConfig {
        applicationId = "tenfen.rodolfo.moisesaichallenge"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":data:itunes:music"))
    implementation(project(":presentation:splash"))
    implementation(project(":presentation:songs"))
    implementation(project(":presentation:album:details"))
    implementation(project(":presentation:theme"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.androidx.navigation.compose)
}
