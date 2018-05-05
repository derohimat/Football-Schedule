package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        var leagueId: String = "",

        @SerializedName("strSport")
        var leagueStr: String = "",

        @SerializedName("strLeague")
        var leagueName: String = "",

        @SerializedName("strLeagueAlternate")
        var leagueBadge: String = ""
)