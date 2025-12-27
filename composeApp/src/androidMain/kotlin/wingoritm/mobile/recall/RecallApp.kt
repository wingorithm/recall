package wingoritm.mobile.recall

import android.app.Application
import org.koin.android.ext.koin.androidContext
import wingoritm.mobile.recall.di.initKoin

class RecallApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RecallApp)
        }
    }
}
