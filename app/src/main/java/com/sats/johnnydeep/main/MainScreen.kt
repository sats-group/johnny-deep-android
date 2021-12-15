package com.sats.johnnydeep.main

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.sats.johnnydeep.R
import com.sats.johnnydeep.history.PreviousIntent
import com.sats.johnnydeep.ui.theme.JohnnyDeepTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
  val context = LocalContext.current
  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  val inputValue = viewModel.inputValue
  val previousIntents = viewModel.previousIntents

  LaunchedEffect(Unit) {
    viewModel.viewEffect.collect { viewEffect ->
      when (viewEffect) {
        is MainViewEffect.StartIntent -> {
          try {
            context.startActivity(Intent(Intent.ACTION_VIEW, viewEffect.uri))
          } catch (activityNotFoundException: ActivityNotFoundException) {
            coroutineScope.launch {
              scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.activity_not_found_toast_message)
              )
            }
          }
        }
        is MainViewEffect.ShowIntentDeletedSnackBar -> {
          coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
              message = context.getString(R.string.previous_intent_deleted),
              actionLabel = context.getString(R.string.previous_intent_deleted_undo_button_label),
              duration = SnackbarDuration.Long,
            )

            when (result) {
              SnackbarResult.ActionPerformed -> viewEffect.undoAction()
              else -> {}
            }
          }
        }
      }
    }
  }

  MainScreenContent(
    scaffoldState = scaffoldState,
    history = {
      History(
        previousIntents = previousIntents,
        onItemClicked = viewModel::onPreviousIntentClicked,
        onRemoveItem = viewModel::onRemovePreviousButtonClicked,
      )
    },
    form = {
      Form(
        inputValue = inputValue,
        onInputChange = viewModel::onInputValueChange,
        onOpenClicked = viewModel::onOpenClicked,
      )
    },
  )
}

@Composable
private fun MainScreenContent(
  scaffoldState: ScaffoldState,
  history: @Composable () -> Unit,
  form: @Composable () -> Unit,
) {
  JohnnyDeepTheme {
    Scaffold(
      scaffoldState = scaffoldState,
      bottomBar = { Spacer(Modifier.navigationBarsWithImePadding()) },
    ) { contentPadding ->
      Column(
        Modifier
          .fillMaxSize()
          .padding(contentPadding)
          .padding(bottom = 80.dp), // make room for the snack bar
      ) {
        Box(Modifier.weight(1f)) {
          history()
        }

        Divider(Modifier.padding(bottom = 16.dp))

        form()
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun History(
  previousIntents: List<PreviousIntent>,
  onItemClicked: (PreviousIntent) -> Unit,
  onRemoveItem: (PreviousIntent) -> Unit,
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = rememberInsetsPaddingValues(
      insets = LocalWindowInsets.current.statusBars,
      additionalTop = 16.dp,
      additionalBottom = 0.dp,
    ),
    reverseLayout = true,
  ) {
    items(previousIntents, key = { it.uri }) { previousIntent ->
      ListItem(
        modifier = Modifier
          .animateItemPlacement()
          .clickable(onClick = { onItemClicked(previousIntent) }),
        text = { Text(previousIntent.uri) },
        secondaryText = { Text(previousIntent.openedAt.toReadableString()) },
        trailing = {
          IconButton(onClick = { onRemoveItem(previousIntent) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
          }
        },
      )
    }
  }
}

@Composable
private fun Form(inputValue: String, onInputChange: (newValue: String) -> Unit, onOpenClicked: () -> Unit) {
  Column(
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .padding(bottom = 0.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
  ) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = inputValue,
      onValueChange = onInputChange,
    )

    Button(
      modifier = Modifier.fillMaxWidth(),
      enabled = inputValue.isNotEmpty(),
      onClick = onOpenClicked,
    ) {
      Text(stringResource(R.string.open_link_button_label))
    }
  }
}

@Composable
private fun Instant.toReadableString(): String {
  val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm")
  val localDateTime = toLocalDateTime(TimeZone.currentSystemDefault())

  return formatter.format(localDateTime.toJavaLocalDateTime())
}
