package net.derohimat.footballschedule.data

import io.reactivex.Observable
import io.reactivex.Single
import net.derohimat.footballschedule.data.model.*
import net.derohimat.footballschedule.data.remote.FootBallApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val mFootBallApi: FootBallApi) {

    fun getLeagueList(): Observable<List<League>> {
        return mFootBallApi.getLeagueList()
                .toObservable()
                .flatMapIterable { leagueResponse -> leagueResponse.leagues }
                .filter { league: League -> league.leagueStr.equals("Soccer") }
                .toList()
                .toObservable()
    }

    fun getTeamList(league: String): Single<List<Team>> {
        return mFootBallApi.getTeamList(league)
                .toObservable()
                .flatMapIterable { teamResponse -> teamResponse.teams }
                .toList()
    }

    fun getEventMatch(leagueId: String, type: Int): Single<EventMatchResponse> {
        return when (type) {
            0 -> mFootBallApi.getPrevMatch(leagueId)
            else -> {
                mFootBallApi.getNextMatch(leagueId)
            }
        }
    }

    fun getEventDetail(eventId: String): Observable<EventMatch> {
        return mFootBallApi.getEventDetail(eventId)
                .toObservable()
                .map { t: EventMatchResponse -> t.events.first() }
    }

    fun getTeamDetail(teamId: String): Observable<Team> {
        return mFootBallApi.getTeamDetail(teamId)
                .toObservable()
                .map { t: TeamResponse -> t.teams.first() }
    }

}