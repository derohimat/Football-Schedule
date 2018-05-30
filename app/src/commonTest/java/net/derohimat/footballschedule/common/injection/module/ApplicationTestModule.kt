package net.derohimat.footballschedule.common.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.remote.FootBallApi
import net.derohimat.footballschedule.injection.ApplicationContext
import org.mockito.Mockito.mock
import javax.inject.Singleton

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
class ApplicationTestModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    /*************
     * MOCKS
     */

    @Provides
    @Singleton
    internal fun providesDataManager(): DataManager {
        return mock(DataManager::class.java)
    }

    @Provides
    @Singleton
    internal fun provideFootballService(): FootBallApi {
        return mock(FootBallApi::class.java)
    }

}
