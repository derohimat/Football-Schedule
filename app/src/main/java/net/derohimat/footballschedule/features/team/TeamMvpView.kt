package net.derohimat.footballschedule.features.team

import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.MvpView

interface TeamMvpView : MvpView {

    fun setupAdapter(data: List<League>)

    fun showNoTeam()

    fun showTeam(data: List<Team>)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}