package com.sats.johnnydeep.di

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import kotlin.reflect.KClass

@Inject
@ContributesBinding(AppScope::class)
class InjectedViewModelFactory(
  override val viewModelProviders: ViewModelProviders,
  override val assistedFactoryProviders: AssistedFactoryProviders,
  override val manualAssistedFactoryProviders: ManualAssistedFactoryProviders,
) : MetroViewModelFactory()

private typealias ViewModelProviders =
  Map<KClass<out ViewModel>, Provider<ViewModel>>

private typealias AssistedFactoryProviders =
  Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>>

private typealias ManualAssistedFactoryProviders =
  Map<KClass<out ManualViewModelAssistedFactory>, Provider<ManualViewModelAssistedFactory>>
