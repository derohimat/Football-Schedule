package net.derohimat.footballschedule.injection.component

import dagger.Component
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.injection.module.ActivityModule
import net.derohimat.footballschedule.injection.module.FragmentModule

@ConfigPersistent
@Component(dependencies = [(AppComponent::class)])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
