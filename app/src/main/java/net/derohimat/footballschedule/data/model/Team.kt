package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("id")
        val id: Int,

        @SerializedName("idTeam")
        var teamId: String = "",

        @SerializedName("strTeam")
        var teamName: String = "",

        @SerializedName("strTeamBadge")
        var teamBadge: String = ""
)