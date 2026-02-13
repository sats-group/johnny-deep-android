package com.sats.johnnydeep.main

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.sats.johnnydeep.R
import com.sats.johnnydeep.history.PreviousIntent
import java.time.format.DateTimeFormatter
import kotlin.time.Instant
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

@Composable
fun MainScreen(
  viewState: MainViewState,
  onUriOpenedSuccessfully: (String) -> Unit,
  onUriFailedToOpen: (String) -> Unit,
  onNoticeDismissed: () -> Unit,
  onPreviousIntentClicked: (PreviousIntent) -> Unit,
  onPreviousIntentRemoved: (PreviousIntent) -> Unit,
  onInputValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val snackbarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  viewState.intentDeletedNotice?.let { intentDeletedNotice ->
    LaunchedEffect(intentDeletedNotice) {
      coroutineScope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()

        val result = snackbarHostState.showSnackbar(
          message = context.getString(R.string.previous_intent_deleted),
          actionLabel = context.getString(R.string.previous_intent_deleted_undo_button_label),
          duration = SnackbarDuration.Short,
          withDismissAction = true,
        )

        if (result == SnackbarResult.ActionPerformed) intentDeletedNotice.undo()

        onNoticeDismissed()
      }
    }
  }

  viewState.intentFailedNotice?.let { intentFailedNotice ->
    LaunchedEffect(intentFailedNotice) {
      coroutineScope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()

        snackbarHostState.showSnackbar(
          message = context.getString(R.string.activity_not_found_toast_message),
        )

        onNoticeDismissed()
      }
    }
  }

  Scaffold(
    modifier = modifier,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      val inputValue = viewState.inputValue

      Form(
        inputValue = inputValue,
        onInputChange = onInputValueChange,
        onOpenClicked = { flag ->
          try {
            val intent = Intent(Intent.ACTION_VIEW, inputValue.toUri()).apply {
              flag?.let { addFlags(it) }
            }

            context.startActivity(intent)

            onUriOpenedSuccessfully(inputValue)
          } catch (_: ActivityNotFoundException) {
            onUriFailedToOpen(inputValue)

            coroutineScope.launch {
              snackbarHostState.showSnackbar(
                context.getString(R.string.activity_not_found_toast_message)
              )
            }
          }
        },
        modifier = Modifier
          .navigationBarsPadding()
          .imePadding(),
      )
    },
  ) { contentPadding ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(contentPadding)
    ) {
      History(
        previousIntents = viewState.previousIntents,
        onItemClicked = onPreviousIntentClicked,
        onRemoveItem = onPreviousIntentRemoved,
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(
          start = contentPadding.calculateStartPadding(LocalLayoutDirection.current),
          end = contentPadding.calculateEndPadding(LocalLayoutDirection.current),
        )
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun History(
  previousIntents: List<PreviousIntent>,
  onItemClicked: (PreviousIntent) -> Unit,
  onRemoveItem: (PreviousIntent) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = contentPadding,
    reverseLayout = true,
  ) {
    items(previousIntents, key = { it.uri }) { previousIntent ->
      ListItem(
        modifier = Modifier
          .animateItem()
          .clickable(onClick = { onItemClicked(previousIntent) }),
        headlineContent = { Text(previousIntent.uri) },
        supportingContent = { Text(previousIntent.openedAt.toReadableString()) },
        trailingContent = {
          IconButton(onClick = { onRemoveItem(previousIntent) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
          }
        },
      )
    }
  }
}

@Composable
private fun Form(
  inputValue: String,
  onInputChange: (newValue: String) -> Unit,
  onOpenClicked: (flag: Int?) -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(tonalElevation = 2.dp) {
    Column(
      modifier = modifier.padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
    ) {
      TextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputValue,
        onValueChange = onInputChange,
        trailingIcon = {
          if (inputValue.isNotEmpty()) {
            IconButton(onClick = { onInputChange("") }) {
              Icon(Icons.Default.Clear, contentDescription = null)
            }
          }
        }
      )

      Row(Modifier.fillMaxWidth()) {
        Button(
          modifier = Modifier.weight(1f),
          enabled = inputValue.isNotEmpty(),
          onClick = { onOpenClicked(null) },
        ) {
          Text(stringResource(R.string.open_link_button_label))
        }

        var isContextMenuOpen by rememberSaveable { mutableStateOf(false) }

        Box {
          IconToggleButton(
            checked = isContextMenuOpen,
            onCheckedChange = { isContextMenuOpen = it },
            enabled = inputValue.isNotEmpty()
          ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
          }

          DropdownMenu(expanded = isContextMenuOpen, onDismissRequest = { isContextMenuOpen = false }) {
            DropdownMenuItem(
              text = { Text("Open with FLAG_ACTIVITY_NEW_TASK") },
              onClick = { onOpenClicked(Intent.FLAG_ACTIVITY_NEW_TASK) }
            )

            DropdownMenuItem(
              text = { Text("Open with FLAG_ACTIVITY_SINGLE_TOP") },
              onClick = { onOpenClicked(Intent.FLAG_ACTIVITY_SINGLE_TOP) }
            )

            DropdownMenuItem(
              text = { Text("Open with FLAG_ACTIVITY_CLEAR_TOP") },
              onClick = { onOpenClicked(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
            )
          }
        }
      }
    }
  }
}

@Composable
private fun Instant.toReadableString(): String {
  val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm")
  val localDateTime = toLocalDateTime(TimeZone.currentSystemDefault())

  return formatter.format(localDateTime.toJavaLocalDateTime())
}
