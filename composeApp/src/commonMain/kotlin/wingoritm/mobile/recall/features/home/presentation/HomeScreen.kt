package wingoritm.mobile.recall.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.data.NoteResponse
import wingoritm.mobile.recall.features.home.presentation.components.HomeAppBar
import wingoritm.mobile.recall.features.home.presentation.components.NoteCard

/**
 * 1. The Entry Point (Stateful, has Koin)
 * // This is what your Navigation Host calls
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    navigateToEditor: (noteId: String?) -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel<HomeScreenViewModel>()
) {
    val notes by viewModel.notes.collectAsState()

    HomeContent(
        notes = notes,
        navigateToEditor = navigateToEditor
    )
}

/**
 * // 2. The Visuals (Stateless, No Koin)
 * // This effectively becomes your "View"
 */
@Composable
fun HomeContent(
    notes: List<NoteResponse>,
    navigateToEditor: (noteId: String?) -> Unit
) {
    Scaffold(
        // 1. The App Bar
        topBar = {
            HomeAppBar(
                isSearchEnabled = false,
                appVersion = "v0.0.1-beta"
            )
        },
        // 2. The Floating Action Button (FAB)
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Napier.i("Floating button clicked navigating to editor...", tag = "HomeScreen")
                    navigateToEditor(null)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            // 1. Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Create your first insights!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // 2. List State
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        onClick = {
                            Napier.i("Note ${note.id} clicked navigating to editor...", tag = "HomeScreen")
                            navigateToEditor(note.id)
                        }
                    )
                }
            }
        }
    }
}

/**
 * preview entry point
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeContent(
            notes = listOf(
                NoteResponse(title = "Mock Note 1", id = "", insight = "mocked", source = "", tags = emptyList(), createdAt = ""),
                NoteResponse(title = "Mock Note 2", id = "", insight = "mocked", source = "", tags = emptyList(), createdAt = ""),
            ),
            navigateToEditor = {}
        )
    }
}