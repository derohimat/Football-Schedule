package net.derohimat.footballschedule.injection.module

import android.app.Activity
import android.app.Fragment
import android.content.Context

import dagger.Module
import dagger.Provides
import net.derohimat.footballschedule.injection.ActivityContext

@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    internal fun providesFragment(): Fragment {
        return mFragment
    }

    @Provides
    internal fun provideActivity(): Activity {
        return mFragment.activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return mFragment.activity
    }

}