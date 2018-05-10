package net.derohimat.footballschedule.features.main

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.MvpView

interface MainMvpView : MvpView {

    fun setupAdapter(data: List<League>)

    fun showEventMatch(data: List<EventMatch>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}