package com.sats.johnnydeep.history

import android.app.Application
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
  version = 1,
  exportSchema = true,
  entities = [
    PreviousIntentEntity::class,
  ],
)
abstract class IntentsDatabase : RoomDatabase() {
  abstract fun intentsDao(): IntentsDao
}

@Dao
interface IntentsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertIntent(intent: PreviousIntentEntity)
}

@Module
@InstallIn(SingletonComponent::class)
class IntentsDatabaseModule {
  @Provides
  fun provideIntentsDatabase(application: Application): IntentsDatabase {
    return Room.databaseBuilder(application, IntentsDatabase::class.java, "intents.db").build()
  }

  @Provides
  @Singleton
  fun provideIntentsDao(database: IntentsDatabase): IntentsDao = database.intentsDao()
}
