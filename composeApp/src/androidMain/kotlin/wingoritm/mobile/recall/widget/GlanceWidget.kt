package wingoritm.mobile.recall.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import org.jetbrains.compose.ui.tooling.preview.Preview
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.core.designSystem.onSurfaceLight
import wingoritm.mobile.recall.core.designSystem.primaryLight
import wingoritm.mobile.recall.core.designSystem.surfaceContainerHighLight
import wingoritm.mobile.recall.core.designSystem.surfaceLight

private fun Color.toProvider() = ColorProvider(day = this, night = this)

class InsightAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            InsightWidgetContent()
        }
    }
}
@Composable
fun InsightWidgetContent(
    date: String = "28 Dec 2025",
    author: String = "me",
    insightTitle: String = "Insight may Consist of 20 words",
    insightBody: String = "description here consist of 20 words too maybe, but you may read more in app so ...",
    tag: String = "#lifestyle"
) {
    // 1. Root Container
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(surfaceLight.toProvider())
            .cornerRadius(32.dp)
            .padding(16.dp)
    ) {
        // 2. Header Row (Date/Author)
        WidgetHeader(author, date)

        // 3. Main Label
        Text(
            text = "Insight of the day:",
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

@Preview
@Composable
fun InsightWidgetPreview() {
    AppTheme {
        InsightWidgetContent()
    }
}