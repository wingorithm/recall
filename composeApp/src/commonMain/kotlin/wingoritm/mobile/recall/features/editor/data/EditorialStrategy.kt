package wingoritm.mobile.recall.features.editor.data

import wingoritm.mobile.recall.data.NoteRepository
import wingoritm.mobile.recall.data.NoteResponse

// The Strategy Interface as The "State Holder" Strategy
sealed interface EditorialStrategy {
    val title: String
    val content: String
    val toolbarTitle: String
    suspend fun onSave(title: String, content: String)
}

// Create Strategy
class CreateModeStrategy(
    private val repo: NoteRepository
) : EditorialStrategy {
    override val title = ""
    override val content = ""
    override val toolbarTitle = "New Note"

    override suspend fun onSave(title: String, content: String) {
        repo.createNote(title, content)
    }
}

// Edit Strategy
class EditModeStrategy(
    private val existingNote: NoteResponse,
    private val repo: NoteRepository
) : EditorialStrategy {
    override val title = existingNote.title
    override val content = existingNote.insight
    override val toolbarTitle = "Edit Note"

    override suspend fun onSave(title: String, content: String) {
        repo.updateNote(existingNote.id, title, content)
    }
}