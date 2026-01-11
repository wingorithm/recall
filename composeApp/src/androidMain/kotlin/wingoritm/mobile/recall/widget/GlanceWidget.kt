package wingoritm.mobile.recall.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import wingoritm.mobile.recall.MainActivity
import wingoritm.mobile.recall.core.designSystem.onSurfaceLight
import wingoritm.mobile.recall.core.designSystem.primaryLight
import wingoritm.mobile.recall.core.designSystem.surfaceContainerHighLight
import wingoritm.mobile.recall.core.designSystem.surfaceLight

// Define keys to store data in the Widget's cache
object InsightWidgetKeys {
    val titleKey = stringPreferencesKey("title")
    val bodyKey = stringPreferencesKey("body")
    val dateKey = stringPreferencesKey("date")
    val authorKey = stringPreferencesKey("author")
    val tagKey = stringPreferencesKey("tag")
}

private fun Color.toProvider() = ColorProvider(day = this, night = this)

class InsightAppWidget : GlanceAppWidget() {

    // Use Preferences to store the widget's state
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            // Read the current state from Preferences
            val prefs = currentState<Preferences>()

            val title = prefs[InsightWidgetKeys.titleKey] ?: "Loading Insight..."
            val body = prefs[InsightWidgetKeys.bodyKey] ?: "Tap to refresh or open app."
            val date = prefs[InsightWidgetKeys.dateKey] ?: "--"
            val author = prefs[InsightWidgetKeys.authorKey] ?: "-"
            val tag = prefs[InsightWidgetKeys.tagKey] ?: "-"
            val isDataEmpty = title == "Loading Insight..."

            InsightWidgetContent(
                date = date,
                author = author,
                insightTitle = title,
                insightBody = body,
                tag = tag,
                isDataEmpty = isDataEmpty
            )
        }
    }
}

@Composable
fun InsightWidgetContent(
    date: String,
    author: String,
    insightTitle: String,
    insightBody: String,
    tag: String,
    isDataEmpty: Boolean
) {
    // 1. Root Container
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(surfaceLight.toProvider())
            .cornerRadius(12.dp)
            .padding(16.dp)
            .clickable(
                actionRunCallback<InsightClickAction>(
                    actionParametersOf(InsightClickAction.IS_DATA_EMPTY_KEY to isDataEmpty)
                )
            )
    ) {
        // 2. Header Row (Date/Author)
        WidgetHeader(author, date)

        // 3. Main Label
        Text(
            text = "Insight Of The Day:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = onSurfaceLight.toProvider()
            ),
            modifier = GlanceModifier.padding(start = 4.dp, top = 8.dp)
        )

        Spacer(modifier = GlanceModifier.height(12.dp))

        // 4. Inner Content Card
        InnerInsightCard(insightTitle, insightBody, tag)
    }
}

@Composable
fun WidgetHeader(author: String, date: String) {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The "by me | Date" Pill
        Column(
            modifier = GlanceModifier
                .background(surfaceContainerHighLight.toProvider())
                .cornerRadius(8.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "by $author  |  $date",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = onSurfaceLight.toProvider()
                )
            )
        }
    }
}

@Composable
fun InnerInsightCard(
    title: String,
    body: String,
    tag: String
) {
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(surfaceContainerHighLight.toProvider())
            .cornerRadius(24.dp)
            .padding(20.dp)
    ) {
        // Title
        Text(
            text = title,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = onSurfaceLight.toProvider()
            )
        )

        Spacer(modifier = GlanceModifier.height(16.dp))

        // Body Description
        Text(
            text = body,
            style = TextStyle(
                fontSize = 14.sp,
                color = onSurfaceLight.toProvider()
            )
        )

        Spacer(modifier = GlanceModifier.height(32.dp))

        // Footer (Tag)
        Column(
            modifier = GlanceModifier
                .background(primaryLight.copy(alpha = 0.1f).toProvider())
                .cornerRadius(50.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = tag,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = onSurfaceLight.toProvider()
                )
            )
        }
    }
}