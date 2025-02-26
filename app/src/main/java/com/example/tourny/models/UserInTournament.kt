package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInTournament(
    @SerialName("uit_id")
    val id: Int,
    @SerialName("uit_scoreUser")
    var scoreUser: Int,
    @SerialName("uit_tournamentId")
    val tournamentId: Int,
    @SerialName("uit_userId")
    val userId: String
)
