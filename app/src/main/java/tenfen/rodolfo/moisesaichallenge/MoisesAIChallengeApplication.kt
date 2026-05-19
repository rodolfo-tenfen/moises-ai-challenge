package tenfen.rodolfo.moisesaichallenge

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tenfen.rodolfo.moisesaichallenge.di.applicationModule

class MoisesAIChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoisesAIChallengeApplication)

            modules(applicationModule)
        }
    }
}
