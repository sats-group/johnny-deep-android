package com.sats.johnnydeep.features.home.impl

import com.sats.core.domain.api.history.models.PreviousIntent

data class HomeViewState(
  val inputValue: String = "",
  val previousIntents: List<PreviousIntent> = emptyList(),
  val intentDeletedNotice: IntentDeletedNotice? = null,
  val intentFailedNotice: IntentFailedNotice? = null,
)

class IntentDeletedNotice(val undo: () -> Unit)

object IntentFailedNotice
