package com.sats.johnnydeep

import android.app.Application
import com.sats.johnnydeep.di.AppGraph
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication

class App : Application(), MetroApplication {
  override val appComponentProviders: MetroAppComponentProviders by lazy {
    createGraphFactory<AppGraph.Factory>().create(this)
  }
}
