import dagger.hilt.android.plugin.HiltExtension

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.dagger.hilt.android)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sats.johnnydeep"

  defaultConfig {
    applicationId = "com.sats.johnnydeep"

    compileSdk = 34
    minSdk = 26
    targetSdk = 34

    versionCode = 1
    versionName = "0.1.0"

    ksp {
      arg("room.schemaLocation", "$projectDir/room-schemas")
    }
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.jetpack.compose.compiler.get()
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

configure<HiltExtension> {
  enableAggregatingTask = true
}

dependencies {
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  coreLibraryDesugaring(libs.android.desugarJdkLibs)
  implementation(libs.accompanist.insetsUi)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.animation)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.material.material)
  implementation(libs.androidx.compose.runtime.runtime)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.ui.ui)
  implementation(libs.androidx.lifecycle.viewModel.compose)
  implementation(libs.androidx.room.runtime)
  implementation(libs.dagger.hilt.android)
  implementation(libs.google.material)
  implementation(libs.kotlin.stdLib)
  implementation(libs.kotlinx.datetime)
  ksp(libs.androidx.room.compiler)
  ksp(libs.dagger.hilt.compiler)
  testImplementation(libs.junit4)
}
