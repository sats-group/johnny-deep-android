package com.sats.johnnydeep.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sats.johnnydeep.ui.theme.JohnnyDeepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()

    super.onCreate(savedInstanceState)

    setContent {
      JohnnyDeepTheme {
        val viewModel: MainViewModel = viewModel()

        MainScreen(
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
  }
}
