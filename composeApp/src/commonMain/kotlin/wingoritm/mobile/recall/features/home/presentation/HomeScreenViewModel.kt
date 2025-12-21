package wingoritm.mobile.recall.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import wingoritm.mobile.recall.data.NoteRepository
import wingoritm.mobile.recall.data.NoteResponse

class HomeScreenViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    // 1. Use StateFlow for reactive UI state
    private val _notes = MutableStateFlow<List<NoteResponse>>(emptyList())
    val notes: StateFlow<List<NoteResponse>> = _notes

    init {
        // 2. Fetch data immediately when ViewModel is created
        // This only happens ONCE, even if you rotate the screen 10 times.
        fetchNotes()
    }

    private fun fetchNotes() {
        Napier.i("fetchNotes() starting...", tag = "HomeViewModel")
        viewModelScope.launch {
            val result = repository.getNotes()
            _notes.value = result
        }
    }
}