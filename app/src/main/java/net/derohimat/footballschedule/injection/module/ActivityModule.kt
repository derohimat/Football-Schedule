package net.derohimat.footballschedule.injection.module

import android.app.Activity
import android.content.Context

import dagger.Module
import dagger.Provides
import net.derohimat.footballschedule.injection.ActivityContext

@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return mActivity
    }
}
