package net.derohimat.footballschedule.features.team.main

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BasePresenter
import net.derohimat.footballschedule.injection.ConfigPersistent
import net.derohimat.footballschedule.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class TeamPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<TeamMvpView>() {

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

    fun getTeams(leagueId: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTeamList(leagueId)
                .compose(SchedulerUtils.ioToMain<List<Team>>())
                .subscribe({ teams ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeam(teams)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

    fun searchTeams(query: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.searchTeam(query)
                .compose(SchedulerUtils.ioToMain<List<Team>>())
                .subscribe({ teams ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeam(teams)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable.message.toString())
                }
    }

}