package net.derohimat.footballschedule.features.team.detail

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class DetailTeamPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<DetailTeamMvpView>() {

    fun getTeamDetail(teamId: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTeamDetail(teamId)
                .compose<Team>(SchedulerUtils.ioToMain<Team>())
                .subscribe({ team ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeam(team)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

}
