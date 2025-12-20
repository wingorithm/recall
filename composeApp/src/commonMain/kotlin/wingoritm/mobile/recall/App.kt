package wingoritm.mobile.recall

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.features.editor.presentation.EditorialScreen
import wingoritm.mobile.recall.features.home.presentation.HomeScreen

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
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    navigateToEditor = {
                        navController.navigate("editor")
                    }
                )
            }
            composable("editor") {
                EditorialScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = {},
                    isPreviewEnabled = false
                )
            }
        }
    }
}