package wingoritm.mobile.recall.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wingoritm.mobile.recall.domain.InsightResponse
import wingoritm.mobile.recall.repository.InsightRepository

sealed interface HomeUiState {
    data object Empty : HomeUiState
    data object Loading : HomeUiState
    data class Success(val insights: List<InsightResponse>) : HomeUiState
}

/**
 * The StateFlow is derived directly from the Repository Flow
 * and Start with Loading
 */
class HomeScreenViewModel(
    private val repository: InsightRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.allInsights
        .map { list ->
            if (list.isEmpty()) HomeUiState.Empty else HomeUiState.Success(list)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        Napier.i("fetchNotes() starting...", tag = "HomeViewModel")
        viewModelScope.launch {
            repository.refreshInsights()
        }
    }
}