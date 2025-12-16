package wingoritm.mobile.recall

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.features.home.presentation.HomeScreen

@Composable
@Preview
fun App() {
    AppTheme {
        HomeScreen()
    }
}