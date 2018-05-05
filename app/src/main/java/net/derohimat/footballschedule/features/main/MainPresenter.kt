package net.derohimat.footballschedule.features.main

import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.Team
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
                .compose(SchedulerUtils.ioToMain<List<String>>())
                .subscribe({ teams ->
                    mvpView?.showProgress(false)
                    mvpView?.setupAdapter(teams)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun getTeams(league: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTeamList(league)
                .compose(SchedulerUtils.ioToMain<List<Team>>())
                .subscribe({ teams ->
                    mvpView?.showProgress(false)
                    mvpView?.showTeams(teams)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}