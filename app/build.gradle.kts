import dagger.hilt.android.plugin.HiltExtension

plugins {
  id("com.android.application")
  id("dagger.hilt.android.plugin")

  kotlin("android")
  kotlin("kapt")
}

android {
  defaultConfig {
    applicationId = "com.sats.johnnydeep"

    compileSdk = 31
    minSdk = 23
    targetSdk = 31

    versionCode = 1
    versionName = "0.1.0"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.jetpack.compose.get()
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  buildTypes {
    named("debug") {
      isDebuggable = true
      isMinifyEnabled = false

      applicationIdSuffix = ".debug"
    }

    named("release") {
      isDebuggable = false
      isMinifyEnabled = true

      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

kapt {
  correctErrorTypes = true
}

configure<HiltExtension> {
  enableAggregatingTask = true
}

dependencies {
  // Accompanist
  implementation(libs.accompanist.insets)
  implementation(libs.accompanist.insetsUi)

  // AndroidX – Activity
  implementation(libs.androidx.activity.compose)

  // AndroidX – Compose
  implementation(libs.androidx.compose.animation)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material.material)
  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.runtime.runtime)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.ui.ui)

  androidTestImplementation(libs.androidx.compose.ui.test.junit4)

  // Dagger
  implementation(libs.dagger.hilt.android)

  kapt(libs.dagger.hilt.compiler)

  // Google Material Design Components
  implementation(libs.google.material)

  // JUnit
  testImplementation(libs.junit4)

  // Kotlin
  implementation(libs.kotlin.stdLib)
}
