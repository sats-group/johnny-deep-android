package com.sats.johnnydeep.core.navigation.entrybuilder

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

inline fun <reified K : NavKey> EntryProviderScope<NavKey>.appEntry(
  noinline content: @Composable (K) -> Unit,
) {
  entry<K> {
    content
  }
}
