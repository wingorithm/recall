package wingoritm.mobile.recall.di

import androidx.room.RoomDatabase
import org.koin.dsl.module
import wingoritm.mobile.recall.database.AppDatabase
import wingoritm.mobile.recall.getDatabaseBuilder

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder()
    }
}