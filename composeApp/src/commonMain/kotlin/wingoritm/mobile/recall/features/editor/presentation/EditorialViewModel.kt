package wingoritm.mobile.recall.features.editor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wingoritm.mobile.recall.data.NoteRepository
import wingoritm.mobile.recall.features.editor.data.CreateModeStrategy
import wingoritm.mobile.recall.features.editor.data.EditModeStrategy
import wingoritm.mobile.recall.features.editor.data.EditorialStrategy
import wingoritm.mobile.recall.features.editor.data.EditorialUIState

class EditorialViewModel(
    private val noteId: String?,
    private val repo: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditorialUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()
    private var strategy: EditorialStrategy? = null

    init {
        initializeScreen()
    }

    private fun initializeScreen() {
        if (noteId == null) {
            // --- CASE 1: CREATE MODE (Synchronous) ---
            val createStrategy = CreateModeStrategy(repo)
            strategy = createStrategy

            _uiState.update {
                it.copy(
                    toolbarTitle = createStrategy.toolbarTitle,
                    isLoading = false
                )
            }
        } else {
            // --- CASE 2: EDIT MODE (Asynchronous) ---
            viewModelScope.launch {
                try {
                    // Fetch data first
                    val note = repo.getNoteDetailById(noteId)

                    //  create the strategy by data
                    val editStrategy = EditModeStrategy(note, repo)
                    strategy = editStrategy

                    // Update UI with fetched data
                    _uiState.update {
                        it.copy(
                            title = editStrategy.title,
                            content = editStrategy.content,
                            toolbarTitle = editStrategy.toolbarTitle,
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    Napier.e("Failed to load note", e)
                }
            }
        }
    }

    // 3. User Actions
    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onContentChange(newContent: String) {
        _uiState.update { it.copy(content = newContent) }
    }

    fun save() {
        val currentState = _uiState.value

        // Guard clause: Don't save if we are still loading
        val currentStrategy = strategy ?: return

        viewModelScope.launch {
            try {
                currentStrategy.onSave(currentState.title, currentState.content)
                Napier.d("Save successful")
                // Navigate back
            } catch (e: Exception) {
                Napier.e("Save failed", e)
            }
        }
    }
}