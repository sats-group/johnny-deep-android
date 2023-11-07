package com.sats.johnnydeep.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.johnnydeep.history.HistoryRepository
import com.sats.johnnydeep.history.PreviousIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainViewState(
  val inputValue: String = "",
  val previousIntents: List<PreviousIntent> = emptyList(),
  val intentDeletedNotice: IntentDeletedNotice? = null,
  val intentFailedNotice: IntentFailedNotice? = null,
)

class IntentDeletedNotice(val undo: () -> Unit)
object IntentFailedNotice

@HiltViewModel
class MainViewModel @Inject constructor(
  private val historyRepository: HistoryRepository,
) : ViewModel() {
  var viewState: MainViewState by mutableStateOf(MainViewState())
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

sealed interface MainViewEffect {
}
