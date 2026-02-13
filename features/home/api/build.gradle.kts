plugins {
  alias(libs.plugins.convention.jvm.library)
  alias(libs.plugins.kotlin.serialization)
}

dependencies {
  api(projects.core.navigation.navKey)
}
