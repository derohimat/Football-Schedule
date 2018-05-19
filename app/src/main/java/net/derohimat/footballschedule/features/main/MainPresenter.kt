package net.derohimat.footballschedule.features.main

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MainMvpView>() {

    fun getLeague() {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getLeagueList()
                .compose(SchedulerUtils.ioToMain<List<League>>())
                .subscribe({ teams ->
                    mvpView?.showProgress(false)
                    mvpView?.setupAdapter(teams)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun getEvent(leagueId: String, type: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getEventMatch(leagueId, type)
                .compose(SchedulerUtils.ioToMain<EventMatchResponse>())
                .subscribe({ eventMatchResponse ->
                    mvpView?.showProgress(false)
                    if(eventMatchResponse.events == null) {
                        mvpView?.showNoMatch()
                    } else {
                        mvpView?.showEventMatch(eventMatchResponse.events)
                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}