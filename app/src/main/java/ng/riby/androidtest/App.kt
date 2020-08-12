package ng.riby.androidtest

import android.app.Application
import ng.riby.androidtest.config.di.dataModule
import ng.riby.androidtest.config.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // initialize koin
        startKoin {
            androidContext(this@App)
            modules(dataModule + presentationModule)
        }
    }
}