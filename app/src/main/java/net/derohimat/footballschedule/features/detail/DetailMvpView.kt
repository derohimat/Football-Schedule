package net.derohimat.footballschedule.features.detail

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showEvent(eventMatch: EventMatch)

    fun showTeam(team: Team, type: Int)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}