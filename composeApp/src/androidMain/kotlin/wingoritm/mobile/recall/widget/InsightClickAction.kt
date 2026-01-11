package wingoritm.mobile.recall.widget

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import wingoritm.mobile.recall.MainActivity

class InsightClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        // Data is empty -> Refresh (Run Worker immediately)
        if (parameters[IS_DATA_EMPTY_KEY] ?: false) {
            val workRequest = OneTimeWorkRequestBuilder<InsightUpdateWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }

        context.startActivity(
            Intent(
                context,
                MainActivity::class.java
            ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        )
    }

    companion object {
        val IS_DATA_EMPTY_KEY = ActionParameters.Key<Boolean>("isDataEmpty")
    }
}