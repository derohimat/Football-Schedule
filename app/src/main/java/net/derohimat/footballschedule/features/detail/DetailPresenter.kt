package net.derohimat.footballschedule.features.detail

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
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
                .compose<EventMatchResponse>(SchedulerUtils.ioToMain<EventMatchResponse>())
                .subscribe({ eventMatch ->
                    mvpView?.showProgress(false)
                    if (eventMatch.events != null) {
                        mvpView?.showEvent(eventMatch.events.first())
                    } else {
                        mvpView?.showError("No Data")
                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
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
                    mvpView?.showError(throwable.message.toString())
                }
    }
}
