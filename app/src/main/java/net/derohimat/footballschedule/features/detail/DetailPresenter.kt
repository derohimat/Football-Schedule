package net.derohimat.footballschedule.features.detail

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class DetailPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<DetailMvpView>() {

    fun getEventDetail(eventId: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getEventDetail(eventId)
                .compose<EventMatch>(SchedulerUtils.ioToMain<EventMatch>())
                .subscribe({ eventMatch ->
                    mvpView?.showProgress(false)
                    mvpView?.showEvent(eventMatch)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun getTeamDetail(teamId: String, type: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTeamDetail(teamId)
                .compose<Team>(SchedulerUtils.ioToMain<Team>())
                .subscribe({ team ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeam(team, type)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }
}
