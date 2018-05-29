package net.derohimat.footballschedule.features.match.main

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.data.model.SearchEventMatchResponse
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MatchPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MatchMvpView>() {

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
                    mvpView?.showError(throwable.message.toString())
                }
    }

    fun getEvent(leagueId: String, type: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getEventMatch(leagueId, type)
                .compose(SchedulerUtils.ioToMain<EventMatchResponse>())
                .subscribe({ eventMatchResponse ->
                    mvpView?.showProgress(false)
                    if (eventMatchResponse.events == null) {
                        mvpView?.showNoMatch()
                    } else {
                        mvpView?.showTeam(eventMatchResponse.events)
                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

    fun searchEvent(query: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.searchEvent(query)
                .compose(SchedulerUtils.ioToMain<SearchEventMatchResponse>())
                .subscribe({ eventMatchResponse ->
                    mvpView?.showProgress(false)
                    if (eventMatchResponse.event == null) {
                        mvpView?.showNoMatch()
                    } else {
                        mvpView?.showTeam(eventMatchResponse.event)
                    }
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

}