plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.compose)
}

android {
  namespace = "com.sats.johnnydeep.core.ui.theme"

  defaultConfig {
    compileSdk = 36
    minSdk = 28
  }
}

dependencies {
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.google.material)
  implementation(platform(libs.androidx.compose.bom))
}
