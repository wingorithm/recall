package wingoritm.mobile.recall.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import wingoritm.mobile.recall.database.AppDatabase
import wingoritm.mobile.recall.database.dao.InsightDao
import wingoritm.mobile.recall.database.getRoomDatabase
import wingoritm.mobile.recall.repository.InsightRepository

expect fun platformModule(): Module

val appModule = module {
    // Networking
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

val databaseModule = module {
    single { getRoomDatabase(get()) }
    single<InsightDao> { get<AppDatabase>().insightDao() }
    single<InsightRepository> {
        InsightRepository(
            dao = get(),
            client = get()
        )
    }
}