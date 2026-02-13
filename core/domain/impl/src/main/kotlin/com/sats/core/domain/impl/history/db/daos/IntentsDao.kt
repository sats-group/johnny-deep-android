package com.sats.core.domain.impl.history.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sats.core.domain.impl.history.db.entities.PreviousIntentEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface IntentsDao {
  @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
  suspend fun upsertIntent(intent: PreviousIntentEntity)

  @Query("SELECT * FROM intents ORDER BY openedAt DESC")
  fun getIntents(): Flow<List<PreviousIntentEntity>>

  @Query("DELETE FROM intents WHERE uri = :uri")
  suspend fun deleteIntent(uri: String)
}
