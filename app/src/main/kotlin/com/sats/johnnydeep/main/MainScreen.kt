package com.sats.johnnydeep.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sats.johnnydeep.features.home.api.HomeNavKey
import com.sats.johnnydeep.features.home.impl.homeScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
  val backStack = rememberNavBackStack(HomeNavKey)

  NavDisplay(
    modifier = modifier,
    backStack = backStack,
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
    ),
    entryProvider = entryProvider {
      homeScreen()
    }
  )
}
