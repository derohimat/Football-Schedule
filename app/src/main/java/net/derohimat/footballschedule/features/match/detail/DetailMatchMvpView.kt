package net.derohimat.footballschedule.features.match.detail

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.TeamDetail
import net.derohimat.footballschedule.features.base.MvpView

interface DetailMatchMvpView : MvpView {

    fun showEvent(eventMatch: EventMatch)

    fun showTeam(team: TeamDetail, type: Int)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}