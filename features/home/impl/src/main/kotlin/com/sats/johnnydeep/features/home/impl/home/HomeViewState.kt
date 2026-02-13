package com.sats.johnnydeep.features.home.impl.home

import com.sats.core.domain.api.history.models.PreviousIntent

internal data class HomeViewState(
  val inputValue: String = "",
  val previousIntents: List<PreviousIntent> = emptyList(),
  val intentDeletedNotice: IntentDeletedNotice? = null,
  val intentFailedNotice: IntentFailedNotice? = null,
)

internal class IntentDeletedNotice(val undo: () -> Unit)

internal object IntentFailedNotice
