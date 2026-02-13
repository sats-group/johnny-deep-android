plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.core.navigation.navKey)
  implementation(libs.androidx.navigation3.runtime)
}
