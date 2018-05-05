package net.derohimat.footballschedule.features.detail

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class DetailPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<DetailMvpView>() {

    fun getTeamDetail(name: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTeamDetail(name)
                .compose<Team>(SchedulerUtils.ioToMain<Team>())
                .subscribe({ team ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeam(team)
//                    for (statistic in team.stats) {
//                        mvpView?.showStat(statistic)
//                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }
}
