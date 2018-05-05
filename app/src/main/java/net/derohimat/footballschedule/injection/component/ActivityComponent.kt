package net.derohimat.footballschedule.injection.component

import dagger.Subcomponent
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.detail.DetailActivity
import net.derohimat.footballschedule.features.main.MainActivity
import net.derohimat.footballschedule.injection.PerActivity
import net.derohimat.footballschedule.injection.module.ActivityModule

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)
}
