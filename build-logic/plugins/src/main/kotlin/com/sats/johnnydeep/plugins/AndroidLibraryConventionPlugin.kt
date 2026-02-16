package com.sats.johnnydeep.plugins

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused") // string-referenced in build.gradle.kts
class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    pluginManager.apply("com.android.library")

    extensions.configure(LibraryExtension::class.java) { extension ->
      extension.compileSdk = 36
      extension.defaultConfig.minSdk = 28
    }
  }
}
