package net.derohimat.footballschedule.common.injection.component

import dagger.Component
import net.derohimat.footballschedule.common.injection.module.ApplicationTestModule
import net.derohimat.footballschedule.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationTestModule::class)])
interface TestComponent : AppComponent