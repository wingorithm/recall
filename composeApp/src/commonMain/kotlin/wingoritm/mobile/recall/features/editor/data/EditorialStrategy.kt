package wingoritm.mobile.recall.features.editor.data

import wingoritm.mobile.recall.domain.InsightResponse
import wingoritm.mobile.recall.repository.InsightRepository

// The Strategy Interface as The "State Holder" Strategy
sealed interface EditorialStrategy {
    val title: String
    val content: String
    val toolbarTitle: String
    val isDeleteDisabled: Boolean
    suspend fun onSave(title: String, content: String)
    suspend fun onDelete()
}

// Create Strategy
class CreateModeStrategy(
    private val repo: InsightRepository
) : EditorialStrategy {
    override val title = ""
    override val content = ""
    override val toolbarTitle = "New Note"
    override val isDeleteDisabled = true

    override suspend fun onSave(title: String, content: String) {
        repo.createInsight(title, content)
    }

    override suspend fun onDelete() {}
}

// Edit Strategy
class EditModeStrategy(
    private val existingNote: InsightResponse,
    private val repo: InsightRepository
) : EditorialStrategy {
    override val title = existingNote.title
    override val content = existingNote.insight
    override val toolbarTitle = "Edit Note"
    override val isDeleteDisabled = false

    override suspend fun onSave(title: String, content: String) {
        repo.updateInsight(existingNote.id, title, content)
    }

    override suspend fun onDelete() {
        repo.delete(existingNote.id)
    }
}