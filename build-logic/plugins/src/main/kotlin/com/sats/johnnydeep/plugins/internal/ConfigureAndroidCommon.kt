package com.sats.johnnydeep.plugins.internal

import com.android.build.api.dsl.CommonExtension

internal fun CommonExtension.configureCommon() {
  compileSdk = 36

  defaultConfig.minSdk = 28
}
