package wingoritm.mobile.recall.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import wingoritm.mobile.recall.features.editor.di.editorialModule
import wingoritm.mobile.recall.features.home.di.homeModule

fun initKoin(config: KoinAppDeclaration? = null) {
    Napier.base(DebugAntilog())
    startKoin {
        config?.invoke(this)
        // LOAD ALL MODULES HERE
        modules(
            platformModule(),
            appModule,  // The Foundation from shared library
            databaseModule,
            homeModule, // home page
            editorialModule, // edit and create page
            // add other modules here...
        )
    }
}