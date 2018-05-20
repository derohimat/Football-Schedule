package net.derohimat.footballschedule.injection.module

import dagger.Module
import dagger.Provides
import net.derohimat.footballschedule.data.remote.FootBallApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by shivam on 8/7/17.
 */
@Module(includes = [(NetworkModule::class)])
class ApiModule {

    @Provides
    @Singleton
    internal fun provideFootBallApi(retrofit: Retrofit): FootBallApi =
            retrofit.create(FootBallApi::class.java)
}