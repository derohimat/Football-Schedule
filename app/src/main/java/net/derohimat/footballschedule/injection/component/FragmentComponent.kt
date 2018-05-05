package net.derohimat.footballschedule.injection.component

import dagger.Subcomponent
import net.derohimat.footballschedule.injection.PerFragment
import net.derohimat.footballschedule.injection.module.FragmentModule

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent