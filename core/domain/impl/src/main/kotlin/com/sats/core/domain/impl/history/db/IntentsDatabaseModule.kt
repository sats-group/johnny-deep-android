package com.sats.core.domain.impl.history.db

import android.app.Application
import androidx.room.Room
import com.sats.core.domain.impl.history.db.daos.IntentsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class IntentsDatabaseModule {
  @Provides
  fun provideIntentsDatabase(application: Application): IntentsDatabase {
    return Room.databaseBuilder(application, IntentsDatabase::class.java, "intents.db").build()
  }

  @Provides
  @Singleton
  fun provideIntentsDao(database: IntentsDatabase): IntentsDao = database.intentsDao()
}
