package net.derohimat.footballschedule.data.model

import com.google.gson.annotations.SerializedName

data class Player(

        @SerializedName("strPlayer")
        val player: String = "",

        @SerializedName("dateBorn")
        val dateBorn: String = "",

        @SerializedName("strNationality")
        val nationality: String = "",

        @SerializedName("strSport")
        val sport: String = "",

        @SerializedName("strWeight")
        val weight: String = "",

        @SerializedName("strInstagram")
        val instagram: String = "",

        @SerializedName("idTeam")
        val idTeam: String = "",

        @SerializedName("strDescriptionEN")
        val descriptionEN: String = "",

        @SerializedName("strBirthLocation")
        val birthLocation: String = "",

        @SerializedName("strWebsite")
        val website: String = "",

        @SerializedName("strHeight")
        val height: String = "",

        @SerializedName("strPosition")
        val position: String = "",

        @SerializedName("strYoutube")
        val youtube: String = "",

        @SerializedName("strCutout")
        val cutout: String = "",

        @SerializedName("strLocked")
        val locked: String = "",

        @SerializedName("intLoved")
        val loved: String = "",

        @SerializedName("strTeam")
        val team: String = "",

        @SerializedName("strTwitter")
        val twitter: String = "",

        @SerializedName("strSigning")
        val signing: String = "",

        @SerializedName("strGender")
        val gender: String = "",

        @SerializedName("strFacebook")
        val Facebook: String = "",

        @SerializedName("idPlayer")
        val idPlayer: String = "",

        @SerializedName("strThumb")
        val thumb: String = "",

        @SerializedName("strWage")
        val wage: String = "",

        @SerializedName("dateSigned")
        val dateSigned: String = ""
)