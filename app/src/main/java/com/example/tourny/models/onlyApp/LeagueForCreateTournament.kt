package com.example.tourny.models.onlyApp

data class LeagueForCreateTournament (
    val leagueId: Int,
    val leagueName: String,
    var changeScore: Boolean,
    var sort:Boolean
)