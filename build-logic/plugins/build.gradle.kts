plugins {
  alias(libs.plugins.kotlin.jvm)
  id("java-gradle-plugin")
}

group = "com.sats.johnnydeep.plugins"

gradlePlugin {
  plugins {
    register("convention-android-application") {
      id = "convention-android-application"
      implementationClass = "com.sats.johnnydeep.plugins.AndroidApplicationConventionPlugin"
    }

    register("convention-android-library") {
      id = "convention-android-library"
      implementationClass = "com.sats.johnnydeep.plugins.AndroidLibraryConventionPlugin"
    }

    register("convention-jvm-library") {
      id = "convention-jvm-library"
      implementationClass = "com.sats.johnnydeep.plugins.JvmLibraryConventionPlugin"
    }
  }
}

dependencies {
  implementation(pluginCoordinates(libs.plugins.android.application))
  implementation(pluginCoordinates(libs.plugins.android.library))
  implementation(pluginCoordinates(libs.plugins.kotlin.jvm))
}

private fun pluginCoordinates(pluginDependency: Provider<PluginDependency>): Provider<String> =
  pluginDependency.map {
    val id = it.pluginId
    val version = it.version

    "$id:$id.gradle.plugin:$version"
  }
