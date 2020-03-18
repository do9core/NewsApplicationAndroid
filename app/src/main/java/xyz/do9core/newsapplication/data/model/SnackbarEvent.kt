package xyz.do9core.newsapplication.data.model

import androidx.annotation.StringRes
import xyz.do9core.liveeventbus.eventbus.LiveEventBus
import xyz.do9core.newsapplication.R

data class SnackbarEvent(
    val text: String? = null,
    @StringRes
    val textRes: Int? = null
) : LiveEventBus.Event {
    init {
        val textNull by lazy { text == null && textRes != null }
        val textResNull by lazy { text != null && textRes == null }
        require(textNull || textResNull) {
            "This event can take either text or textRes."
        }
    }
}

fun textSnackEvent(text: String): SnackbarEvent {
    val msg = text.takeIf { it.isNotBlank() }
    return SnackbarEvent(
        msg,
        R.string.app_save_favourite_failed.takeIf { msg == null }
    )
}

fun resSnackEvent(@StringRes resId: Int) = SnackbarEvent(textRes = resId)