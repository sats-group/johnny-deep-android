package com.sats.johnnydeep.history

import kotlinx.datetime.Clock
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val intentsDao: IntentsDao) {
  suspend fun addOrUpdateIntent(uri: String) {
    val entity = PreviousIntentEntity(uri = uri, openedAt = Clock.System.now().toString())

    intentsDao.upsertIntent(entity)
  }
}
