package com.sats.johnnydeep.main

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.sats.johnnydeep.R
import com.sats.johnnydeep.ui.theme.JohnnyDeepTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
  val context = LocalContext.current
  var textFieldValue by remember { mutableStateOf("") }
  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()

  JohnnyDeepTheme {
    Scaffold(
      scaffoldState = scaffoldState,
      snackbarHost = {
        SnackbarHost(modifier = Modifier.navigationBarsWithImePadding(), hostState = it)
      }
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .navigationBarsWithImePadding()
          .padding(horizontal = 56.dp)
          .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
      ) {
        TextField(
          modifier = Modifier.fillMaxWidth(),
          value = textFieldValue,
          onValueChange = { textFieldValue = it },
        )

        Button(
          modifier = Modifier.fillMaxWidth(),
          enabled = textFieldValue.isNotEmpty(),
          onClick = {
            try {
              context.startActivity(Intent(Intent.ACTION_VIEW, textFieldValue.toUri()))
            } catch (activityNotFoundException: ActivityNotFoundException) {
              coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                  context.getString(R.string.activity_not_found_toast_message)
                )
              }
            }
          },
        ) {
          Text(stringResource(R.string.open_link_button_label))
        }
      }
    }
  }
}
