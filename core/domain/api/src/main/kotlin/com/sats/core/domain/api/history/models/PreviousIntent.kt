package com.sats.core.domain.api.history.models

import kotlin.time.Instant

data class PreviousIntent(val uri: String, val openedAt: Instant)
