package net.derohimat.footballschedule

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.singhajit.sherlock.core.Sherlock
import com.squareup.leakcanary.LeakCanary
import com.tspoon.traceur.Traceur
import net.derohimat.footballschedule.injection.component.AppComponent
import net.derohimat.footballschedule.injection.component.DaggerAppComponent
import net.derohimat.footballschedule.injection.module.AppModule
import net.derohimat.footballschedule.injection.module.NetworkModule
import timber.log.Timber

class FootballApplication : MultiDexApplication() {

    private var mAppComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
            Sherlock.init(this)
            Traceur.enableLogging()
        }
    }

    // Needed to replace the component with a test specific one
    var component: AppComponent
        get() {
            if (mAppComponent == null) {
                mAppComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .networkModule(NetworkModule(this))
                        .build()
            }
            return mAppComponent as AppComponent
        }
        set(appComponent) {
            mAppComponent = appComponent
        }

    companion object {

        operator fun get(context: Context): FootballApplication {
            return context.applicationContext as FootballApplication
        }
    }
}
