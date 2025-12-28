package wingoritm.mobile.recall.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class InsightWidgetReceiver : GlanceAppWidgetReceiver() {

    // 1. Define which Widget UI to use
    override val glanceAppWidget: GlanceAppWidget = InsightAppWidget()

    // 2. Koin Injection Helper (since we are in a BroadcastReceiver context)
//    private val getInsightUseCase: GetDailyInsightUseCase by inject()

    // 3. OPTIONAL: You might need to override onUpdate to fetch data
    // and store it in GlanceStateDefinition or Preferences if the data fetching is heavy.
}