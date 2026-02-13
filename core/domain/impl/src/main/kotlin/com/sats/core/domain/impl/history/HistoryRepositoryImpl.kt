package com.sats.core.domain.impl.history

import com.sats.core.domain.api.history.HistoryRepository
import com.sats.core.domain.api.history.models.PreviousIntent
import com.sats.core.domain.impl.history.db.daos.IntentsDao
import com.sats.core.domain.impl.history.db.entities.PreviousIntentEntity
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.ansman.dagger.auto.AutoBind

@OptIn(ExperimentalTime::class)
@AutoBind
internal class HistoryRepositoryImpl @Inject constructor(
  private val intentsDao: IntentsDao,
) : HistoryRepository {
  override fun getIntentsHistory(): Flow<List<PreviousIntent>> {
    return intentsDao.getIntents().map { intents ->
      intents.map { entity ->
        PreviousIntent(
          uri = entity.uri,
          openedAt = Instant.parse(entity.openedAt),
        )
      }
    }
  }

  override suspend fun addOrUpdateIntent(uri: String) {
    val entity = PreviousIntentEntity(uri = uri, openedAt = Clock.System.now().toString())

    intentsDao.upsertIntent(entity)
  }

  override suspend fun addOrUpdateIntent(previousIntent: PreviousIntent) {
    val entity = PreviousIntentEntity(uri = previousIntent.uri, openedAt = previousIntent.openedAt.toString())

    intentsDao.upsertIntent(entity)
  }

  override suspend fun removePreviousIntent(previousIntent: PreviousIntent) {
    intentsDao.deleteIntent(previousIntent.uri)
  }
}
