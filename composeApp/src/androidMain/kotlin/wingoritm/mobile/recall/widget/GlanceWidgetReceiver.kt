package wingoritm.mobile.recall.widget

import android.content.Context
import java.util.concurrent.TimeUnit
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

class InsightWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = InsightAppWidget()

    // Schedule the worker when the first widget is placed
    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        val workRequest = PeriodicWorkRequestBuilder<InsightUpdateWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "InsightWidgetUpdate",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

}