package net.derohimat.footballschedule.features.main

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchFav
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.MvpView

interface MainMvpView : MvpView {

    fun setupAdapter(data: List<League>)

    fun showNoMatch()

    fun showEventMatch(data: List<EventMatch>)

    fun showEventMatchFav(data: List<EventMatchFav>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}