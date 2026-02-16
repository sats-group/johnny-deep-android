package com.sats.johnnydeep.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.sats.johnnydeep.plugins.internal.configureCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

@Suppress("unused") // string-referenced in build.gradle.kts
class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit = with(target) {
    pluginManager.apply("com.android.application")

    extensions.configure(ApplicationExtension::class.java) { extension ->
      extension.configureCommon()

      extension.defaultConfig.targetSdk = 36

      extension.buildTypes {
        named("debug") {
          it.isDebuggable = true
          it.isMinifyEnabled = false

          it.applicationIdSuffix = ".debug"
        }

        named("release") {
          it.isDebuggable = false
          it.isMinifyEnabled = true

          it.proguardFiles(extension.getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
      }
    }

    extensions.configure(KotlinAndroidExtension::class.java) { extension ->
      extension.configureCommon()
    }
  }
}
