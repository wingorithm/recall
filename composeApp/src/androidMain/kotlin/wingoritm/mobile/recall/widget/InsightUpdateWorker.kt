package wingoritm.mobile.recall.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import wingoritm.mobile.recall.repository.InsightRepository
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

val FriendlyDateFormat = LocalDate.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    chars(", ")
    day()
    char(' ')
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    year()
}

class InsightUpdateWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {
    // Inject Repository using KoinComponent
    private val repository: InsightRepository by inject()

    @OptIn(ExperimentalTime::class)
    override suspend fun doWork(): Result {
        return try {
            // 1. Fetch Data
            val insights = repository.allInsights.firstOrNull()

            if (insights.isNullOrEmpty()) {
                return Result.success()
            }

            // 2. Pick Random
            val randomInsight = insights.random()
            val instant = Instant.parse(randomInsight.createdAt)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            // 3. Update State for ALL instances of this widget
            val glanceManager = GlanceAppWidgetManager(context)
            val glanceIds = glanceManager.getGlanceIds(InsightAppWidget::class.java)

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[InsightWidgetKeys.titleKey] = randomInsight.title
                    prefs[InsightWidgetKeys.bodyKey] = randomInsight.insight
                    prefs[InsightWidgetKeys.dateKey] = localDateTime.date.format(FriendlyDateFormat)
                    prefs[InsightWidgetKeys.authorKey] = "me"
                    prefs[InsightWidgetKeys.tagKey] = randomInsight.tags.firstOrNull() ?: "#general"
                }
                // Trigger redraw
                InsightAppWidget().update(context, glanceId)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}