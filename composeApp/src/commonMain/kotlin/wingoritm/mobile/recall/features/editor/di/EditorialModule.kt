package wingoritm.mobile.recall.features.editor.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import wingoritm.mobile.recall.features.editor.presentation.EditorialViewModel

val editorialModule = module {
    viewModel { (noteId: String?) ->
        EditorialViewModel(
            noteId = noteId,
            repo = get()
        )
    }
}