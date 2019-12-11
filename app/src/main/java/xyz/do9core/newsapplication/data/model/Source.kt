package xyz.do9core.newsapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Source(
    var id: String? = null,
    @PrimaryKey
    var name: String
)
