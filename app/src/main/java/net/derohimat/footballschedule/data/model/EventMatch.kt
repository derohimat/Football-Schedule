package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class EventMatch(
        @SerializedName("intHomeShots")
        val homeShots: String? = null,

        @SerializedName("strSport")
        val sport: String? = null,

        @SerializedName("strHomeLineupDefense")
        val homeLineupDefense: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        val awayLineupSubstitutes: String? = null,

        @SerializedName("idLeague")
        val idLeague: String? = null,

        @SerializedName("strHomeLineupForward")
        val homeLineupForward: String? = null,

        @SerializedName("strTVStation")
        val tVStation: Any? = null,

        @SerializedName("strHomeGoalDetails")
        val homeGoalDetails: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        val awayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupMidfield")
        val awayLineupMidfield: String? = null,

        @SerializedName("idEvent")
        val idEvent: String? = null,

        @SerializedName("intRound")
        val round: String? = null,

        @SerializedName("strHomeYellowCards")
        val homeYellowCards: String? = null,

        @SerializedName("idHomeTeam")
        val idHomeTeam: String? = null,

        @SerializedName("intHomeScore")
        val homeScore: String? = null,

        @SerializedName("dateEvent")
        val dateEvent: String? = null,

        @SerializedName("strCountry")
        val country: Any? = null,

        @SerializedName("strAwayTeam")
        val awayTeam: String? = null,

        @SerializedName("strHomeLineupMidfield")
        val homeLineupMidfield: String? = null,

        @SerializedName("strDate")
        val date: String? = null,

        @SerializedName("strHomeFormation")
        val homeFormation: String? = null,

        @SerializedName("strMap")
        val map: Any? = null,

        @SerializedName("idAwayTeam")
        val idAwayTeam: String? = null,

        @SerializedName("strAwayRedCards")
        val awayRedCards: String? = null,

        @SerializedName("strBanner")
        val banner: Any? = null,

        @SerializedName("strFanart")
        val fanart: Any? = null,

        @SerializedName("strDescriptionEN")
        val descriptionEN: Any? = null,

        @SerializedName("strResult")
        val result: Any? = null,

        @SerializedName("strCircuit")
        val circuit: Any? = null,

        @SerializedName("intAwayShots")
        val awayShots: String? = null,

        @SerializedName("strFilename")
        val filename: String? = null,

        @SerializedName("strTime")
        val time: String? = null,

        @SerializedName("strAwayGoalDetails")
        val awayGoalDetails: String? = null,

        @SerializedName("strAwayLineupForward")
        val awayLineupForward: String? = null,

        @SerializedName("strLocked")
        val locked: String? = null,

        @SerializedName("strSeason")
        val season: String? = null,

        @SerializedName("intSpectators")
        val spectators: String? = null,

        @SerializedName("strHomeRedCards")
        val homeRedCards: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        val homeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        val homeLineupSubstitutes: String? = null,

        @SerializedName("strAwayFormation")
        val awayFormation: String? = null,

        @SerializedName("strEvent")
        val event: String? = null,

        @SerializedName("strAwayYellowCards")
        val awayYellowCards: String? = null,

        @SerializedName("strAwayLineupDefense")
        val awayLineupDefense: String? = null,

        @SerializedName("strHomeTeam")
        val homeTeam: String? = null,

        @SerializedName("strThumb")
        val thumb: Any? = null,

        @SerializedName("strLeague")
        val league: String? = null,

        @SerializedName("intAwayScore")
        val awayScore: String? = null,

        @SerializedName("strCity")
        val City: String? = null,

        @SerializedName("strPoster")
        val Poster: String? = null
)