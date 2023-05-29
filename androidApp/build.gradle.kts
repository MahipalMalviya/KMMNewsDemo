plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

android {
    namespace = "com.cerence.kmmnewssample.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.cerence.kmmnewssample.android"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":shared"))

    with(ComposeDestination){
        implementation(core)
        implementation(composeDestination)
        ksp(composeDestinationPlugin)
    }
    with(Material3) {
        implementation(material3)
        implementation(window)
    }
    with(Accompanist){
        implementation(coil)
        implementation(webview)
    }
    with(Compose){
        implementation(composeUI)
        implementation(util)
        implementation(composeActivity)
        implementation(composeMaterial)
        implementation(composeToolingDebug)
        debugImplementation(composeToolingDebug)

    }
    implementation(Koin.koinAndroid)
//    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
//    implementation("androidx.compose.material:material:1.2.1")
//    implementation("androidx.activity:activity-compose:1.5.1")
}