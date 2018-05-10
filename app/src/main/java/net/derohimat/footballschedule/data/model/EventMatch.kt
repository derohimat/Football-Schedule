package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class EventMatch(
        @SerializedName("idEvent")
        var eventId: String = "",

        @SerializedName("strEvent")
        var eventName: String = "",

        @SerializedName("strHomeTeam")
        var homeTeam: String = "",

        @SerializedName("strAwayTeam")
        var awayTeam: String = "",

        @SerializedName("intHomeScore")
        var homeScore: String = "",

        @SerializedName("intAwayScore")
        var awayScore: String = "",

        @SerializedName("idHomeTeam")
        var homeTeamId: String = "",

        @SerializedName("idAwayTeam")
        var awayTeamId: String = "",

        @SerializedName("dateEvent")
        var eventDate: String = "",

        @SerializedName("strTime")
        var eventTime: String = "",

        @SerializedName("strLeague")
        var leagueName: String = ""
)