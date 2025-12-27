package wingoritm.mobile.recall.features.home.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import wingoritm.mobile.recall.features.home.presentation.HomeScreenViewModel

val homeModule = module {
    viewModelOf(::HomeScreenViewModel)
}