enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
  }
}

includeBuild("build-logic")

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  repositories {
    google()
    mavenCentral()
    mavenLocal()
  }
}

rootProject.name = "johnny-deep"

include(":app")
include(":core:domain:api")
include(":core:domain:impl")
include(":core:ui:theme")
include(":features:home:impl")
