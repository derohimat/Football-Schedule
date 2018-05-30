package net.derohimat.footballschedule.features.team.detail

import net.derohimat.footballschedule.data.model.TeamDetail
import net.derohimat.footballschedule.features.base.MvpView

interface DetailTeamMvpView : MvpView {

    fun showTeam(team: TeamDetail)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}