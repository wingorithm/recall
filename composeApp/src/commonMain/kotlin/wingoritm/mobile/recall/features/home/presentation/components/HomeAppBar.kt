package wingoritm.mobile.recall.features.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    isSearchEnabled: Boolean = false,
    appVersion: String = "1.0.0"
) {
    var showInfoTooltip by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val popupYOffset = remember(density) { with(density) { 40.dp.roundToPx() } }

    TopAppBar(
        title = {
            Text(
                text = "Recall Notes",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                // Ensure text is visible (assuming dark theme based on image)
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        actions = {
            // --- SEARCH BUTTON ---
            // 1. Controlled by boolean flag
            FilledTonalIconButton(
                onClick = { /* Search Action */ },
                enabled = isSearchEnabled,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }

            // --- INFO BUTTON ---
            Box {
                FilledTonalIconButton(
                    onClick = { showInfoTooltip = !showInfoTooltip },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }

                // 2. Custom Popup "Tooltip"
                if (showInfoTooltip) {
                    Popup(
                        onDismissRequest = { showInfoTooltip = false },
                        alignment = Alignment.TopEnd,
                        offset = IntOffset(x = 0, y = popupYOffset)
                    ) {
                        // The actual tooltip bubble UI
                        Surface(
                            modifier = Modifier.padding(top = 10.dp), // Offset from button
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = "Recall app version: $appVersion",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 0.dp),
    )
}