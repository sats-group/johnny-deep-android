package com.sats.johnnydeep.features.home.impl.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.core.domain.api.history.HistoryRepository
import com.sats.core.domain.api.history.models.PreviousIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
  private val historyRepository: HistoryRepository,
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
}
