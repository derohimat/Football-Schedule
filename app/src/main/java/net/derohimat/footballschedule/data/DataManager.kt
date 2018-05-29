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
                .filter { league: League -> league.leagueStr == "Soccer" }
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

    fun getEventDetail(eventId: String): Single<EventMatchResponse> {
        return mFootBallApi.getEventDetail(eventId)
    }

    fun getTeamDetail(teamId: String): Observable<TeamDetail> {
        return mFootBallApi.getTeamDetail(teamId)
                .toObservable()
                .map { t: TeamDetailResponse -> t.teams.first() }
    }

}