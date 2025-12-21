package wingoritm.mobile.recall

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.features.editor.presentation.EditorialScreen
import wingoritm.mobile.recall.features.home.presentation.HomeScreen

@Serializable
object HomeRoute

@Serializable
data class EditorialRoute(val noteId: String?)

/**
 * {navController} is the controller that manages the back stack
 * {navHost} is the container that swaps screens, where has several screens routes
 */
@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    navigateToEditor = { noteId ->
                        navController.navigate(EditorialRoute(noteId))
                    }
                )
            }

            composable<EditorialRoute> { backStackEntry ->
                EditorialScreen(
                    onBackClick = { navController.popBackStack() },
                    isPreviewEnabled = false,
                    noteId = backStackEntry.toRoute<EditorialRoute>().noteId,
                )
            }
        }
    }
}