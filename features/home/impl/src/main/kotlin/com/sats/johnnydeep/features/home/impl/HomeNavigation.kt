package com.sats.johnnydeep.features.home.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.sats.johnnydeep.features.home.api.HomeNavKey
import dev.zacsweers.metrox.viewmodel.metroViewModel

fun EntryProviderScope<NavKey>.homeScreen() {
  entry<HomeNavKey> {
    val viewModel: HomeViewModel = metroViewModel()

    HomeScreen(
      viewState = viewModel.viewState,
      onUriOpenedSuccessfully = viewModel::onUriOpenedSuccessfully,
      onUriFailedToOpen = viewModel::onUriFailedToOpen,
      onNoticeDismissed = viewModel::onNoticeDismissed,
      onPreviousIntentClicked = viewModel::onPreviousIntentClicked,
      onPreviousIntentRemoved = viewModel::onPreviousIntentRemoved,
      onInputValueChange = viewModel::onInputValueChange,
    )
  }
}
