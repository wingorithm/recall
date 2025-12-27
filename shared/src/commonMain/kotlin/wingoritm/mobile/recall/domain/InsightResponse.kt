package wingoritm.mobile.recall.domain

import kotlinx.serialization.Serializable

@Serializable
data class InsightResponse(
    val id: String,
    val title: String,
    val insight: String,
    val source: String,
    val tags: List<String>,
    val createdAt: String
)