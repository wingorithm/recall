package wingoritm.mobile.recall

import android.app.Application
import wingoritm.mobile.recall.di.initKoin

class RecallApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
