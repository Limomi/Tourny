package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tour(
    @SerialName("t_id")
    val id: Int,
    @SerialName("t_tournamentId")
    val tournamentId: Int,
    @SerialName("t_name")
    val name: String,
    @SerialName("t_closed")
    val closed: Boolean
)
