package wingoritm.mobile.recall.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import wingoritm.mobile.recall.database.entity.InsightEntity

@Dao
interface InsightDao {

    // Create / Update
    @Upsert
    suspend fun saveInsight(insight: InsightEntity)

    // Create / Update for sync to BE
    @Upsert
    suspend fun saveAll(insights: List<InsightEntity>)

    // Read (Single)
    @Query("SELECT * FROM insights WHERE id = :id")
    suspend fun getInsightById(id: String): InsightEntity?

    // Read (All - Reactive)
    @Query("SELECT * FROM insights ORDER BY createdAt DESC")
    fun getAllInsights(): Flow<List<InsightEntity>>

    // Delete by ID (Custom Query)
    @Query("DELETE FROM insights WHERE id = :id")
    suspend fun deleteInsightById(id: String)
}