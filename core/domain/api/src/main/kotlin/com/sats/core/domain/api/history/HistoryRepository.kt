package com.sats.core.domain.api.history

import com.sats.core.domain.api.history.models.PreviousIntent
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
  fun getIntentsHistory(): Flow<List<PreviousIntent>>

  suspend fun addOrUpdateIntent(uri: String)

  suspend fun addOrUpdateIntent(previousIntent: PreviousIntent)

  suspend fun removePreviousIntent(previousIntent: PreviousIntent)
}

