plugins {
    alias(libs.plugins.gradle)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.cvopa.peter.billduAsignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cvopa.peter.billduAsignment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    //timber
    implementation(libs.timber)

    // lc
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.savestate)
    ksp(libs.lifecycle.compiler)

    //hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.lifecycle)
    implementation(libs.hilt.compose)


    // dagger
    implementation(libs.dagger.base)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)

    //core
    implementation(libs.core)

    //navigation
    implementation(libs.navigation)

    //compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3")

    //room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
}