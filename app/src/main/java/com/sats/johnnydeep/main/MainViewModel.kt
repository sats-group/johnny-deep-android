package com.sats.johnnydeep.main

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.johnnydeep.history.HistoryRepository
import com.sats.johnnydeep.history.PreviousIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val historyRepository: HistoryRepository,
) : ViewModel() {
  private val _viewEffect = Channel<MainViewEffect>()
  val viewEffect: Flow<MainViewEffect> = _viewEffect.receiveAsFlow()

  var previousIntents: List<PreviousIntent> by mutableStateOf(emptyList())
    private set

  var inputValue: String by mutableStateOf("")
    private set

  init {
    viewModelScope.launch {
      historyRepository.getIntentsHistory().collect { previousIntents = it }
    }
  }

  fun onInputValueChange(newValue: String) {
    inputValue = newValue
  }

  fun onPreviousIntentClicked(previousIntent: PreviousIntent) {
    inputValue = previousIntent.uri
  }

  fun onOpenClicked() {
    viewModelScope.launch {
      historyRepository.addOrUpdateIntent(inputValue)
      _viewEffect.send(MainViewEffect.StartIntent(inputValue.toUri()))
    }
  }

  fun onRemovePreviousButtonClicked(previousIntent: PreviousIntent) {
    viewModelScope.launch {
      historyRepository.removePreviousIntent(previousIntent)
    }
  }
}

sealed interface MainViewEffect {
  data class StartIntent(val uri: Uri) : MainViewEffect
}
