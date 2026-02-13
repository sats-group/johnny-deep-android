plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sats.johnnydeep.features.home.impl"

  defaultConfig {
    compileSdk = 36
    minSdk = 26
  }
}

dependencies {
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.core)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.dagger.hilt.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.datetime)
  implementation(platform(libs.androidx.compose.bom))
  implementation(projects.core.domain.api)
  ksp(libs.dagger.hilt.compiler)
}
