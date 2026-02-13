package com.sats.core.domain.impl.history.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sats.core.domain.impl.history.db.daos.IntentsDao
import com.sats.core.domain.impl.history.db.entities.PreviousIntentEntity

@Database(
  version = 1,
  exportSchema = true,
  entities = [
    PreviousIntentEntity::class,
  ],
)
internal abstract class IntentsDatabase : RoomDatabase() {
  abstract fun intentsDao(): IntentsDao
}
