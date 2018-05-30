package net.derohimat.footballschedule.injection.component

import dagger.Subcomponent
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.match.detail.DetailMatchActivity
import net.derohimat.footballschedule.features.match.main.MatchActivity
import net.derohimat.footballschedule.features.player.list.PlayerActivity
import net.derohimat.footballschedule.features.team.detail.DetailTeamActivity
import net.derohimat.footballschedule.features.team.main.TeamActivity
import net.derohimat.footballschedule.injection.PerActivity
import net.derohimat.footballschedule.injection.module.ActivityModule

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(teamActivity: TeamActivity)

    fun inject(detailTeamActivity: DetailTeamActivity)

    fun inject(matchActivity: MatchActivity)

    fun inject(detailMatchActivity: DetailMatchActivity)

    fun inject(playerActivity: PlayerActivity)
}
