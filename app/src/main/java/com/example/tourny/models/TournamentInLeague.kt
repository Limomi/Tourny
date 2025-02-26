package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TournamentInLeague(
    @SerialName("til_changeScore")
    val changeScore: Boolean,
    @SerialName("til_leagueId")
    val leagueId:Int,
    @SerialName("til_sort")
    val sort: Boolean,
    @SerialName("til_tournamentId")
    val tournamentId: Int
)
