package wingoritm.mobile.recall.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import wingoritm.mobile.recall.features.editor.di.editorialModule
import wingoritm.mobile.recall.features.home.di.homeModule

val appModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                // Connect Ktor's Logger to Napier
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v("HTTP Client", null, message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }
}

fun initKoin(config: KoinAppDeclaration? = null) {
    // Initialize Logging
    Napier.base(DebugAntilog())
    startKoin {
        config?.invoke(this) // Used for Android Context later

        // 👇 LOAD ALL MODULES HERE
        modules(
            appModule,  // The Foundation
            homeModule, // home page
            editorialModule, // edit and create page
            // add other modules here...
        )
    }
}