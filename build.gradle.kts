buildscript {
  repositories {
    gradlePluginPortal()
    google()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:7.0.1")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
