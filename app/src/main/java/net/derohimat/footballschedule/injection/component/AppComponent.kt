package net.derohimat.footballschedule.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.remote.FootBallApi
import net.derohimat.footballschedule.injection.ApplicationContext
import net.derohimat.footballschedule.injection.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun pokemonApi(): FootBallApi
}
