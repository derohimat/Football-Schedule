package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class TeamDetail(

        @SerializedName("intStadiumCapacity")
        val stadiumCapacity: String = "",

        @SerializedName("strTeamShort")
        val teamShort: String = "",

        @SerializedName("intFormedYear")
        val formedYear: String = "",

        @SerializedName("strSport")
        val sport: String = "",

        @SerializedName("idTeam")
        val idTeam: String = "",

        @SerializedName("strDescriptionEN")
        val descriptionEN: String = "",

        @SerializedName("strTeamJersey")
        val teamJersey: String = "",

        @SerializedName("strWebsite")
        val website: String = "",

        @SerializedName("strStadiumDescription")
        val stadiumDescription: String = "",

        @SerializedName("idLeague")
        val idLeague: String = "",

        @SerializedName("strTeam")
        val team: String = "",

        @SerializedName("strTeamLogo")
        val teamLogo: String = "",

        @SerializedName("strDivision")
        val division: Any = "",

        @SerializedName("strStadium")
        val stadium: String = "",

        @SerializedName("strStadiumLocation")
        val stadiumLocation: String = "",

        @SerializedName("strTeamBadge")
        val teamBadge: String = "",

        @SerializedName("strCountry")
        val country: String = "",

        @SerializedName("strTeamBanner")
        val teamBanner: String = "",

        @SerializedName("strLeague")
        val league: String = "",

        @SerializedName("strManager")
        val manager: String = "",

        @SerializedName("strKeywords")
        val keywords: String = "",

        @SerializedName("strStadiumThumb")
        val stadiumThumb: String = ""
)