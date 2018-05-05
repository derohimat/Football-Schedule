package net.derohimat.footballschedule.features.detail

import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showTeam(team: Team)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}