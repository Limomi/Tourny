package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tournament (
    @SerialName("t_adminId")
    val adminId: String,
    @SerialName("t_id")
    val id: Int,
    @SerialName("t_name")
    val name: String,
    @SerialName("t_joinCode")
    val joinCode: String,
    @SerialName("t_roundCount")
    val roundCount: Int,
    @SerialName("t_closeTournament")
    var closedTournament: Boolean,
    @SerialName("t_typeParings")
    val typeParing: String
)