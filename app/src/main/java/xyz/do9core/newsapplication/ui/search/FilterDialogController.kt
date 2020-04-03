package xyz.do9core.newsapplication.ui.search

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FilterDialogController<T>(
    context: Context,
    title: String,
    okText: String,
    cancelText: String,
    private var selectionIndex: Int,
    items: List<T>,
    mapper: (T) -> String,
    onConfirm: (resultPosition: Int) -> Unit
) {
    private val displayItems = items.map(mapper).toTypedArray()
    private val builder = MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setSingleChoiceItems(displayItems, selectionIndex) { _, pos -> selectionIndex = pos }
        .setPositiveButton(okText) { _, _ -> onConfirm(selectionIndex) }
        .setNegativeButton(cancelText) { _, _ -> }

    fun show() {
        builder.create().show()
    }

    class Wrapper<T>(private val context: Context) {

        var title: String? = null

        @StringRes
        var titleRes: Int? = null

        var okText: String? = null

        @StringRes
        var okRes: Int? = null

        var cancelText: String? = null

        @StringRes
        var cancelRes: Int? = null

        var items: List<T> = emptyList()
        var selectionIndex = -1

        private var mapper: (T) -> String = { it.toString() }
        private var onConfirm: (Int) -> Unit = {}

        fun mapItem(mapper: (T) -> String) {
            this.mapper = mapper
        }

        fun onConfirm(callback: (Int) -> Unit) {
            this.onConfirm = callback
        }

        fun build(): FilterDialogController<T> {
            return FilterDialogController(
                context = context,
                title = fall(title, titleRes, ""),
                okText = fall(okText, okRes, "OK"),
                cancelText = fall(cancelText, cancelRes, "CANCEL"),
                items = items,
                mapper = mapper,
                onConfirm = onConfirm,
                selectionIndex = selectionIndex
            )
        }

        private fun fall(raw: String?, res: Int?, default: String): String {
            return when {
                raw != null -> raw
                res != null -> context.getString(res)
                else -> default
            }
        }
    }
}

fun <T> filterDialog(
    context: Context,
    builder: FilterDialogController.Wrapper<T>.() -> Unit
): FilterDialogController<T> = FilterDialogController.Wrapper<T>(context).apply(builder).build()