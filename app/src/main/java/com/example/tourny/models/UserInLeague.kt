package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInLeague(
    @SerialName("uil_leagueId")
    val leagueId: Int,
    @SerialName("uil_score")
    var score: Int,
    @SerialName("uil_userId")
    val userId: String
)
