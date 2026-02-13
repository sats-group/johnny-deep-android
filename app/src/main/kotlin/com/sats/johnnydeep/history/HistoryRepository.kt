package com.sats.johnnydeep.history

import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepository @Inject constructor(private val intentsDao: IntentsDao) {
  fun getIntentsHistory(): Flow<List<PreviousIntent>> {
    return intentsDao.getIntents().map { intents ->
      intents.map { entity ->
        PreviousIntent(
          uri = entity.uri,
          openedAt = Instant.parse(entity.openedAt),
        )
      }
    }
  }

  suspend fun addOrUpdateIntent(uri: String) {
    val entity = PreviousIntentEntity(uri = uri, openedAt = Clock.System.now().toString())

    intentsDao.upsertIntent(entity)
  }

  suspend fun addOrUpdateIntent(previousIntent: PreviousIntent) {
    val entity = PreviousIntentEntity(uri = previousIntent.uri, openedAt = previousIntent.openedAt.toString())

    intentsDao.upsertIntent(entity)
  }

  suspend fun removePreviousIntent(previousIntent: PreviousIntent) {
    intentsDao.deleteIntent(previousIntent.uri)
  }
}
