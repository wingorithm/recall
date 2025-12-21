package wingoritm.mobile.recall.features.home.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import wingoritm.mobile.recall.data.NoteRepository
import wingoritm.mobile.recall.features.home.presentation.HomeScreenViewModel

val homeModule = module {
    // 1. Define the Repository as a Singleton (creates one instance for the whole app)
    single<NoteRepository> { NoteRepository() }

    // 2. Define the ViewModel
    // Koin will automatically find the 'NoteRepository' above and inject it.
    viewModelOf(::HomeScreenViewModel)
}