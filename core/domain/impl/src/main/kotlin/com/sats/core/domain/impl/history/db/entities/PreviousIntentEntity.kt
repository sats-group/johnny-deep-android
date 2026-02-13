package com.sats.core.domain.impl.history.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intents")
data class PreviousIntentEntity(
  @PrimaryKey
  val uri: String,
  val openedAt: String,
)
