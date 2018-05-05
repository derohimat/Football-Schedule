package net.derohimat.footballschedule.features.main

import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.MvpView

interface MainMvpView : MvpView {

    fun setupAdapter(data: List<String>)

    fun showTeams(data: List<Team>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}