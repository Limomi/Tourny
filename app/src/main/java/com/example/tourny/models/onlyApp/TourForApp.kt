package com.example.tourny.models.onlyApp

data class TourForApp (
    val id: Int,
    val name: String,
    val closed: Boolean,
    val tournamentId: Int,
    var parings: MutableList<ParingWithName>
)