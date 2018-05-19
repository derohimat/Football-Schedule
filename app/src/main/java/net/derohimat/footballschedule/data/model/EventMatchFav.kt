package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class EventMatchFav(
        @SerializedName("id")
        val id: Int,

        @SerializedName("idEvent")
        val idEvent: String = "",

        @SerializedName("strEvent")
        val event: String? = null,

        @SerializedName("dateEvent")
        val dateEvent: String? = null,

        @SerializedName("strHomeTeam")
        val homeTeam: String? = null,

        @SerializedName("intHomeScore")
        val homeScore: String? = null,

        @SerializedName("strAwayTeam")
        val awayTeam: String? = null,

        @SerializedName("intAwayScore")
        val awayScore: String? = null
)