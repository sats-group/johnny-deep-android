plugins {
  alias(libs.plugins.convention.jvm.library)
  alias(libs.plugins.kotlin.compose)
}

dependencies {
  api(libs.androidx.navigation3.runtime)
  api(projects.core.navigation.navKey)
}
