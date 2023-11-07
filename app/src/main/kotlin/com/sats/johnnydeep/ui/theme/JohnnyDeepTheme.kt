package com.sats.johnnydeep.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun JohnnyDeepTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
  val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

  MaterialTheme(
    colorScheme = colorScheme,
    content = content,
  )
}
