package com.sats.johnnydeep.features.home.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.core.domain.api.history.HistoryRepository
import com.sats.core.domain.api.history.models.PreviousIntent
import com.sats.johnnydeep.features.home.api.HomeNavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.launch

@AssistedInject
class HomeViewModel(
  private val historyRepository: HistoryRepository,
  @Assisted val navKey: HomeNavKey,
) : ViewModel() {
  var viewState: HomeViewState by mutableStateOf(HomeViewState())
    private set

  init {
    viewModelScope.launch {
      historyRepository.getIntentsHistory().collect {
        viewState = viewState.copy(previousIntents = it)
      }
    }
  }

  fun onInputValueChange(newValue: String) {
    viewState = viewState.copy(inputValue = newValue)
  }

  fun onPreviousIntentClicked(previousIntent: PreviousIntent) {
    viewState = viewState.copy(inputValue = previousIntent.uri)
  }

  fun onUriOpenedSuccessfully(uri: String) {
    viewModelScope.launch {
      historyRepository.addOrUpdateIntent(uri)
    }
  }

  fun onUriFailedToOpen(uri: String) {
    viewModelScope.launch {
      historyRepository.addOrUpdateIntent(uri)

      viewState = viewState.copy(
        intentFailedNotice = IntentFailedNotice
      )
    }
  }

  fun onPreviousIntentRemoved(previousIntent: PreviousIntent) {
    viewModelScope.launch {
      historyRepository.removePreviousIntent(previousIntent)

      viewState = viewState.copy(
        intentDeletedNotice = IntentDeletedNotice(
          undo = {
            viewModelScope.launch {
              historyRepository.addOrUpdateIntent(previousIntent)
            }
          }
        )
      )
    }
  }

  fun onNoticeDismissed() {
    viewState = viewState.copy(
      intentFailedNotice = null,
      intentDeletedNotice = null,
    )
  }

  @AssistedFactory
  @ManualViewModelAssistedFactoryKey(Factory::class)
  @ContributesIntoMap(AppScope::class)
  fun interface Factory : ManualViewModelAssistedFactory {
    fun create(navKey: HomeNavKey): HomeViewModel
  }
}
