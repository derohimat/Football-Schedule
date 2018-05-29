package net.derohimat.footballschedule.features.match.main

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchFav
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.MvpView

interface MatchMvpView : MvpView {

    fun setupAdapter(data: List<League>)

    fun showNoMatch()

    fun showTeam(data: List<EventMatch>)

    fun showEventMatchFav(data: List<EventMatchFav>)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}