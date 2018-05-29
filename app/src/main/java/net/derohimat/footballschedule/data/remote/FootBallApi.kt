package net.derohimat.footballschedule.data.remote

import io.reactivex.Single
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.data.model.LeagueResponse
import net.derohimat.footballschedule.data.model.TeamDetailResponse
import net.derohimat.footballschedule.data.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FootBallApi {

    @GET("all_leagues.php")
    fun getLeagueList(): Single<LeagueResponse>

    @GET("search_all_teams.php")
    fun getTeamList(@Query("l") league: String): Single<TeamResponse>

    @GET("eventspastleague.php")
    fun getPrevMatch(@Query("id") leagueId: String): Single<EventMatchResponse>

    @GET("eventsnextleague.php")
    fun getNextMatch(@Query("id") leagueId: String): Single<EventMatchResponse>

    @GET("lookupevent.php")
    fun getEventDetail(@Query("id") teamId: String): Single<EventMatchResponse>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") teamId: String): Single<TeamDetailResponse>

}
