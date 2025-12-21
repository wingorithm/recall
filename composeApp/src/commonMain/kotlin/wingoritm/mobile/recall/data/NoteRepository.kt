package wingoritm.mobile.recall.data

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Since this is a simple fetch, Client setup and the Fetch function are combined, in a Repository class for simplicity.
 * In a larger app, put the HttpClient in core/network.
 */
class NoteRepository {
    // Initialize Ktor Client
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Safe against API changes
                prettyPrint = true
            })
        }
    }

    suspend fun getNotes(): List<NoteResponse> {
        return try {
            // Ktor automatically parses the JSON array into List<Note>
            Napier.i("Fetching notes from Mock API...", tag = "NoteRepository")
            client.get("https://69411b8d686bc3ca8165a574.mockapi.io/insight").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Return empty list on error for now
        }
    }

    suspend fun getNoteDetailById(noteId: String): NoteResponse {
        return try {
            Napier.i("Fetching note detail from Mock API...", tag = "NoteRepository")
    //            client.get("https://69411b8d686bc3ca8165a574.mockapi.io/insight").body()
            NoteResponse(
                id = noteId, // Use the ID passed in
                title = "The Pragmatic Programmer",
                insight = "Broken windows theory suggests that visible signs of crime...",
                source = "Book",
                tags = listOf("Engineering", "Career", "Philosophy"),
                createdAt = "2025-12-21T10:00:00Z"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            NoteResponse(
                id = "",
                title = "",
                insight = "",
                source = "",
                tags = emptyList(),
                createdAt = ""
            )
        }
    }

    suspend fun createNote(title: String, content: String){
        Napier.i("createNote...", tag = "NoteRepository")
    }

    suspend fun updateNote(id: String, title: String, content: String){
        Napier.i("updateNote...", tag = "NoteRepository")
    }
}
