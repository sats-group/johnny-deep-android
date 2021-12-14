package com.sats.johnnydeep.main

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.johnnydeep.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val historyRepository: HistoryRepository,
) : ViewModel() {
  private val _viewEffect = Channel<MainViewEffect>()
  val viewEffect: Flow<MainViewEffect> = _viewEffect.receiveAsFlow()

  var inputValue: String by mutableStateOf("")
    private set

  fun onInputValueChange(newValue: String) {
    inputValue = newValue
  }

  fun onOpenClicked() {
    viewModelScope.launch {
      historyRepository.addOrUpdateIntent(inputValue)
      _viewEffect.send(MainViewEffect.StartIntent(inputValue.toUri()))
    }
  }
}

sealed interface MainViewEffect {
  data class StartIntent(val uri: Uri) : MainViewEffect
}
