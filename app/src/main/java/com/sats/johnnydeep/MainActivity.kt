package com.sats.johnnydeep

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.sats.johnnydeep.ui.theme.JohnnyDeepTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      MainScreen()
    }
  }
}

@Composable
private fun MainScreen() {
  val context = LocalContext.current
  var textFieldValue by remember { mutableStateOf("") }

  JohnnyDeepTheme {
    Surface {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .navigationBarsWithImePadding()
          .padding(56.dp),
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
              Toast
                .makeText(context, context.getString(R.string.activity_not_found_toast_message), Toast.LENGTH_SHORT)
                .show()
            }
          },
        ) {
          Text(stringResource(R.string.open_link_button_label))
        }
      }
    }
  }
}
