package xyz.do9core.newsapplication.data.model

import xyz.do9core.liveeventbus.eventbus.LiveEventBus

data class SnackbarEvent(
    val text: String? = null,
    val textRes: Int? = null
) : LiveEventBus.Event