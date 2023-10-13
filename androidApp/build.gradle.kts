plugins {
    id("dev.sierov.android.application")
    id("dev.sierov.kotlin.android")
    id("dev.sierov.compose.multiplatform")
    alias(libs.plugins.dependency.graph)
}

android {
    namespace = "dev.sierov.jumbo"
    defaultConfig {
        applicationId = "dev.sierov.jumbo"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.activity)
    implementation(libs.circuit.foundation)
    implementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.androidx.compose.test)
    androidTestImplementation(libs.okhttp.mockwebserver)
    debugImplementation(libs.androidx.compose.testManifest)
}