package wingoritm.mobile.recall.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val insight: String,
    val source: String,
    val tags: List<String>,
    val createdAt: String
)