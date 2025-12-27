package wingoritm.mobile.recall.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

/**
 * Simple converter to store List<String> as a JSON String
 * Example: ["a", "b"] -> "[\"a\", \"b\"]"
 */
class RoomConverters {

    @TypeConverter
    fun fromTagsList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toTagsList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}