package xyz.do9core.newsapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Country constructor(
    @PrimaryKey
    @ColumnInfo(name = "code")
    var code: String
) {
    object China : Country("cn")
    object UnitedStates : Country("us")

    companion object {
        fun values() = listOf(
            China,
            UnitedStates
        )
    }
}