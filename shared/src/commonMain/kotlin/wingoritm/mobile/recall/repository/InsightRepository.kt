package wingoritm.mobile.recall.repository

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wingoritm.mobile.recall.database.dao.InsightDao
import wingoritm.mobile.recall.database.entity.InsightEntity
import wingoritm.mobile.recall.domain.InsightResponse
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * "Offline-First" Repository pattern
 */
class InsightRepository(
    private val dao: InsightDao,
    private val client: HttpClient
) {

    val allInsights: Flow<List<InsightResponse>> = dao.getAllInsights().map {
        entities -> entities.map { it.toDomain() }
    }

    /**
     * Sync Network -> Save to DB
     * TODO: This function doesn't return data. It just updates the DB.
     */
    suspend fun refreshInsights() { return }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    suspend fun createInsight(title: String, insight: String) {
        val currentTime: String = Clock.System.now().toString()
        val newEntity = InsightEntity(
            id = Uuid.random().toString(),
            title = title,
            insight = insight,
            source = "",
            tags = List(0) { "" }.toMutableList(),
            updateAt = currentTime,
            createdAt = currentTime
        )
        dao.saveInsight(newEntity)
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    suspend fun updateInsight(id: String, title: String, insight: String) {
        val entity = dao.getInsightById(id) ?: throw Exception("Insight with ID $id not found")
        val newEntity = InsightEntity(
            id = id,
            title = title,
            insight = insight,
            source = entity.source,
            tags = entity.tags,
            updateAt = Clock.System.now().toString(),
            createdAt = entity.createdAt
        )
        dao.saveInsight(newEntity)
    }

    suspend fun delete(id: String) {
        dao.deleteInsightById(id)
    }

    suspend fun getInsightById(id: String): InsightResponse {
        val entity = dao.getInsightById(id) ?: throw Exception("Insight with ID $id not found")
        return InsightResponse(
            id = id,
            title = entity.title,
            insight = entity.insight,
            source = entity.source,
            tags = entity.tags,
            createdAt = entity.createdAt
        )
    }

    private fun InsightEntity.toDomain() = InsightResponse(
        id = id, title = title, insight = insight,
        source = source, tags = tags, createdAt = createdAt
    )
}