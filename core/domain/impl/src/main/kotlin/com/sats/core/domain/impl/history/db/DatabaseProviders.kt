package com.sats.core.domain.impl.history.db

import android.app.Application
import androidx.room.Room
import com.sats.core.domain.impl.history.db.daos.IntentsDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface DatabaseProviders {
  @SingleIn(AppScope::class)
  @Provides
  fun provideDatabase(application: Application): IntentsDatabase {
    return Room.databaseBuilder(application, IntentsDatabase::class.java, "intents.db").build()
  }

  @Provides
  fun provideIntentsDao(database: IntentsDatabase): IntentsDao = database.intentsDao()
}
