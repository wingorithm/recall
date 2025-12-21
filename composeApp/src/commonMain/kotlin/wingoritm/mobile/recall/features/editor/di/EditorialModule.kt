package wingoritm.mobile.recall.features.editor.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import wingoritm.mobile.recall.data.NoteRepository
import wingoritm.mobile.recall.features.editor.presentation.EditorialViewModel

val editorialModule = module {
    single<NoteRepository> { NoteRepository() }
    viewModel { (noteId: String?) ->
        EditorialViewModel(
            noteId = noteId,
            repo = get()
        )
    }
}