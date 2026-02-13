plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.metro)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sats.johnnydeep.core.domain.impl"

  defaultConfig {
    compileSdk = 36
    minSdk = 28
  }
}

kotlin {
  jvmToolchain(11)
}

ksp {
  arg("room.schemaLocation", "$projectDir/room-schemas")
}

dependencies {
  coreLibraryDesugaring(libs.android.desugarJdkLibs)
  implementation(libs.androidx.room.runtime)
  implementation(libs.kotlinx.coroutines.core)
  implementation(projects.core.domain.api)
  ksp(libs.androidx.room.compiler)
}
