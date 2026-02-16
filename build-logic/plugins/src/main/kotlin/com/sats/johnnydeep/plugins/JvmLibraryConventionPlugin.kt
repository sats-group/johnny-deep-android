package com.sats.johnnydeep.plugins

import com.sats.johnnydeep.plugins.internal.configureCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

@Suppress("unused") // string-referenced in build.gradle.kts
class JvmLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    pluginManager.apply("org.jetbrains.kotlin.jvm")

    extensions.configure(KotlinJvmProjectExtension::class.java) { extension ->
      extension.configureCommon()
    }
  }
}
