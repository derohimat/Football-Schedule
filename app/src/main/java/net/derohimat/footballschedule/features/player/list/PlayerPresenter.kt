package net.derohimat.footballschedule.features.player.list

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class PlayerPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<PlayerMvpView>() {

    fun getPlayers(teamName: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getPlayerList(teamName)
                .compose(SchedulerUtils.ioToMain<List<Player>>())
                .subscribe({ players ->
                    mvpView?.showProgress(false)
                    when {
                        players.isEmpty() ->
                            mvpView?.showNoPlayer()
                        else ->
                            mvpView?.showPlayer(players)
                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

}