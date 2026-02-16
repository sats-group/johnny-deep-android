package com.sats.johnnydeep.plugins.internal

import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

internal fun KotlinBaseExtension.configureCommon() {
  jvmToolchain(11)
}
