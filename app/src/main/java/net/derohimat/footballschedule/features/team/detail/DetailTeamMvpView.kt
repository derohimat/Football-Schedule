package net.derohimat.footballschedule.features.team.detail

import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.MvpView

interface DetailTeamMvpView : MvpView {

    fun showTeam(team: Team)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}