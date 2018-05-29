package net.derohimat.footballschedule.data.remote

import io.reactivex.Single
import net.derohimat.footballschedule.data.model.*
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

    @GET("searchplayers.php")
    fun getPlayers(@Query("t") teamName: String): Single<PlayerResponse>

    @GET("searchevents.php")
    fun searchEvent(@Query("e") query: String): Single<SearchEventMatchResponse>

}
