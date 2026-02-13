plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.dagger.hilt.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sats.johnnydeep"

  defaultConfig {
    applicationId = "com.sats.johnnydeep"

    compileSdk = 36
    minSdk = 26
    targetSdk = 36

    versionCode = 1
    versionName = "0.1.0"
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

      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

kotlin {
  jvmToolchain(11)
}

dependencies {
  implementation(libs.androidx.activity.compose)
  implementation(libs.dagger.hilt.android)
  implementation(platform(libs.androidx.compose.bom))
  implementation(projects.core.domain.api)
  implementation(projects.core.domain.impl)
  implementation(projects.core.ui.theme)
  implementation(projects.features.home.impl)
  ksp(libs.dagger.hilt.compiler)
}
