package wingoritm.mobile.recall.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Entity(tableName = "insights")
data class InsightEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val insight: String,
    val source: String,
    val tags: List<String>,
    val updateAt: String,
    val createdAt: String
)