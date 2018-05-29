package net.derohimat.footballschedule.injection.component

import dagger.Subcomponent
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.match.detail.DetailMatchMatchActivity
import net.derohimat.footballschedule.features.match.main.MatchActivity
import net.derohimat.footballschedule.features.team.TeamActivity
import net.derohimat.footballschedule.injection.PerActivity
import net.derohimat.footballschedule.injection.module.ActivityModule

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(teamActivity: TeamActivity)

    fun inject(matchActivity: MatchActivity)

    fun inject(detailMatchActivity: DetailMatchMatchActivity)
}
