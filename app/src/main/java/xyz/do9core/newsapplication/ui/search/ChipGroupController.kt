package xyz.do9core.newsapplication.ui.search

import android.content.Context
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChipGroupController<T : Any>(
    private val context: Context,
    private val title: CharSequence,
    private val chipGroup: ChipGroup,
    private val dataList: List<T>,
    mapper: (T) -> CharSequence = { it.toString() }
) {
    private var checkedIndex = -1
    private val displayArray = dataList.map(mapper).toTypedArray()

    fun showDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setSingleChoiceItems(displayArray, checkedIndex) { _, pos -> checkedIndex = pos }
            .setPositiveButton("OK") { _, _ ->
                chipGroup.removeAllViews()
                val checkedItem = displayArray.getOrNull(checkedIndex) ?: return@setPositiveButton
                val chip = Chip(context).apply {
                    text = checkedItem
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        checkedIndex = -1
                        chipGroup.removeView(it)
                    }
                }
                chipGroup.addView(chip)
            }
            .setNegativeButton("Cancel") { _, _ -> Unit }
            .show()
    }

    fun getSelection(): T? = dataList.getOrNull(checkedIndex)
}