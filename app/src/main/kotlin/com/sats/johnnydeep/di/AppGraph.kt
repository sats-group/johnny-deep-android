package com.sats.johnnydeep.di

import android.app.Application
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

@DependencyGraph(AppScope::class)
interface AppGraph : MetroAppComponentProviders, ViewModelGraph {
  @DependencyGraph.Factory
  interface Factory {
    fun create(@Provides application: Application): AppGraph
  }
}
