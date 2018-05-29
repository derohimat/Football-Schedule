package net.derohimat.footballschedule.features.player.list

import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.features.base.MvpView

interface PlayerMvpView : MvpView {

    fun showNoPlayer()

    fun showPlayer(data: List<Player>)

    fun showProgress(show: Boolean)

    fun showError(message: String)

}