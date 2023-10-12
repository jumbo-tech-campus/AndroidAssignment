plugins {
    id("dev.sierov.android.application")
    id("dev.sierov.kotlin.android")
    id("dev.sierov.compose.multiplatform")
}

android {
    namespace = "dev.sierov.jumbo"
    defaultConfig {
        applicationId = "dev.sierov.jumbo"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(projects.shared)
}