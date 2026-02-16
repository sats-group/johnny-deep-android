plugins {
  alias(libs.plugins.convention.android.library)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.metro)
}

android {
  namespace = "com.sats.johnnydeep.features.home.impl"
}

dependencies {
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.core)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.datetime)
  implementation(libs.metrox.android)
  implementation(libs.metrox.viewmodel.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(projects.core.domain.api)
}
